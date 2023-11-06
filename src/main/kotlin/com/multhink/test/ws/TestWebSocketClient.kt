/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.test.ws

import io.micronaut.websocket.annotation.ClientWebSocket
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import org.reactivestreams.Publisher
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

@ClientWebSocket
abstract class TestWebSocketClient : AutoCloseable {
    private val messageHistory: Deque<String> = ConcurrentLinkedDeque()
    val latestMessage: String? get() = messageHistory.peekLast()
    val messagesChronologically: List<String> get() = ArrayList(messageHistory)

    @OnOpen
    fun onOpen() {}

    @OnMessage
    fun onMessage(message: String) {
        messageHistory.add(message)
    }

    @OnClose
    fun onClose() {}

    abstract fun broadcast(message: String): Publisher<String>
    abstract fun send(message: String): Publisher<String>
}
