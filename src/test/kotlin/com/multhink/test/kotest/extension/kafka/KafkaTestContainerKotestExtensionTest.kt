/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.test.kotest.extension.kafka

import io.kotest.core.spec.style.FreeSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class KafkaTestContainerKotestExtensionTest: FreeSpec({
    beforeSpec {
        KafkaTestContainerKotestExtension.beforeProject()
    }

    "kafka test container kotest extension" - {
        "should be running" {
            assert(KafkaTestContainerKotestExtension.kafka.isRunning())
        }
    }

    afterSpec {
        KafkaTestContainerKotestExtension.afterProject()
    }
})
