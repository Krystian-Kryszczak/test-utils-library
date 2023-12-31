/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.test.kotest.extension.cassandra

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.Session
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.multhink.commons.utils.readJarResourceFile
import io.kotest.core.extensions.Extension
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.BeforeProjectListener
import org.testcontainers.containers.CassandraContainer
import org.testcontainers.utility.DockerImageName
import java.io.File

object CassandraTestContainerKotestExtension : Extension, BeforeProjectListener, AfterProjectListener {
    val cassandra = CassandraContainer(DockerImageName.parse("cassandra:5.0"))

    private fun getSession(): Session = Cluster.builder()
        .addContactPoint(cassandra.host)
        .withPort(cassandra.getMappedPort(CassandraContainer.CQL_PORT))
        .withoutJMXReporting()
        .build()
        .connect()

    override suspend fun beforeProject() {
        var hostPath = "${System.getProperty("user.dir")}/src/main/resources/cassandra.yaml"
        if (!File(hostPath).isFile) {
            readJarResourceFile(this::javaClass::class.java, "cassandra.yaml")?.let { bytes ->
                File.createTempFile("cassandra", ".yaml").apply { deleteOnExit() }.let { tempFile ->
                    tempFile.writeBytes(bytes)
                    hostPath = tempFile.absolutePath
                }
            }
        }
        val containerPath = "/etc/cassandra/cassandra.yaml"
        cassandra.withFileSystemBind(hostPath, containerPath)
        cassandra.start()

        getSession().execute(
            SchemaBuilder.createKeyspace(System.getProperty("CASSANDRA_KEYSPACE", "app")).ifNotExists()
                .withSimpleStrategy(2)
                .build()
                .query
        )

        System.setProperty("CASSANDRA_HOST", cassandra.host)
        System.setProperty("CASSANDRA_PORT", cassandra.getMappedPort(CassandraContainer.CQL_PORT).toString())
    }

    override suspend fun afterProject() {
        cassandra.stop()
    }
}
