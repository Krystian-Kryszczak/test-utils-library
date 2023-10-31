/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.test

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.kotest.core.spec.style.StringSpec

@MicronautTest
class TestUtilsLibraryTest(private val application: EmbeddedApplication<*>): StringSpec({

    "test the server is running" {
        assert(application.isRunning)
    }
})
