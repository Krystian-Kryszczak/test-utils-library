/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.test.kotest.extension.cassandra

import io.kotest.core.spec.style.FreeSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class CassandraTestContainerKotestExtensionTest: FreeSpec({
    "cassandra test container kotest extension" - {
        "should be running" {
            assert(CassandraTestContainerKotestExtension.cassandra.isRunning())
        }
    }
})
