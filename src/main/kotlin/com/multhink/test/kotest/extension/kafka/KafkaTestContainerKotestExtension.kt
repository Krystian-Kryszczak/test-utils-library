/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.test.kotest.extension.kafka

import io.kotest.core.extensions.Extension
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.BeforeProjectListener
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName

object KafkaTestContainerKotestExtension : Extension, BeforeProjectListener, AfterProjectListener {
    val kafka = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.1"))

    override suspend fun beforeProject() {
        kafka.start()

        System.setProperty("KAFKA_HOST", kafka.host)
        System.setProperty("KAFKA_PORT", kafka.getMappedPort(KafkaContainer.KAFKA_PORT).toString())
    }

    override suspend fun afterProject() {
        kafka.stop()
    }
}
