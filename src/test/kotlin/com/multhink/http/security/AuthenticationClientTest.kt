/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.http.security

import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.authentication.AuthenticationFailureReason
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Flowable

@MicronautTest
class AuthenticationClientTest(authenticationClient: AuthenticationClient): FreeSpec({
    "authentication client login" - {
        "should return valid bearer access refresh token" {
            val token = authenticationClient.login(
                UsernamePasswordCredentials("john.smith@example.com", "sherlock")
            )
            token.username shouldBe "john.smith@example.com"
            token.accessToken.shouldNotBeBlank()
            token.tokenType shouldBe "Bearer"
        }

        "should throw http client exception response with message 'Credentials Do Not Match'" {
            shouldThrowWithMessage<HttpClientResponseException>("Credentials Do Not Match") {
                authenticationClient.login(
                    UsernamePasswordCredentials("jack.smith@example.com", "holmes")
                )
            }
        }
    }
}) {
    @MockBean(AuthenticationProvider::class)
    fun authenticationProvider(): AuthenticationProvider<HttpRequest<*>> {
        val provider = mockk<AuthenticationProvider<HttpRequest<*>>>()

        every {
            provider.authenticate(any(), any())
        } returns Flowable.just(AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH))
        every {
            provider.authenticate(any(), UsernamePasswordCredentials("john.smith@example.com", "sherlock"))
        } returns Flowable.just(AuthenticationResponse.success("john.smith@example.com"))

        return provider
    }
}
