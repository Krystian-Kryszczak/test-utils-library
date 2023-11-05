/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.test.ws

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.micronaut.websocket.WebSocketClient
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.ServerWebSocket
import io.reactivex.rxjava3.core.Single
import org.reactivestreams.Publisher
import org.testcontainers.shaded.org.awaitility.Awaitility.await

@Secured(SecurityRule.IS_ANONYMOUS)
@ServerWebSocket("/ws/test")
class TestWebSocketServer {
    @OnMessage
    fun onMessage(message: String, session: WebSocketSession): Publisher<String> = session.send(message)
}

@MicronautTest
class TestingWebSocketClient(@Client("/ws/test") webSocketClient: WebSocketClient): FreeSpec({
    "testing web socket client" - {
        "should successfully send message to the ws server and then receive response" {
            // given
            val client = Single.fromPublisher(webSocketClient.connect(TestWebSocketClient::class.java, "/")).blockingGet()
            val messageToSend = "Hello world!"

            // when
            client.send(messageToSend)
                .test()
                .await()

            // then
            .assertValue(messageToSend)
            await().until { client.latestMessage != null }
            client.latestMessage shouldBe messageToSend
        }

        "should successfully send broadcast message to the ws server and then receive response" {
            // given
            val client = Single.fromPublisher(webSocketClient.connect(TestWebSocketClient::class.java, "/")).blockingGet()
            val messageToSend = "Hello world!"

            // when
            client.broadcast(messageToSend)
                .test()
                .await()

            // then
            .assertValue(messageToSend)
            await().until { client.latestMessage != null }
            client.latestMessage shouldBe messageToSend
        }
    }
})
