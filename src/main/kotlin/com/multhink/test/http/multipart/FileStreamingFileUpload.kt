/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.test.http.multipart

import io.micronaut.http.MediaType
import io.micronaut.http.multipart.PartData
import io.micronaut.http.multipart.StreamingFileUpload
import io.reactivex.rxjava3.core.Flowable
import org.reactivestreams.Subscriber
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.util.Optional

class FileStreamingFileUpload(private val file: File): StreamingFileUpload {
    override fun getContentType() = Optional.of(MediaType.forFilename(file.name))

    override fun getName(): String = file.name

    override fun getFilename(): String = name

    override fun getSize() = file.length()

    override fun getDefinedSize() = getSize()

    override fun isComplete() = file.exists()

    override fun subscribe(subscriber: Subscriber<in PartData>) {
        val contentType = contentType
        Flowable.just(
            object : PartData {
                override fun getInputStream(): InputStream = file.inputStream()

                override fun getBytes(): ByteArray = file.readBytes()

                override fun getByteBuffer(): ByteBuffer = ByteBuffer.wrap(bytes)

                override fun getContentType(): Optional<MediaType> = contentType
        }).subscribe(subscriber)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("transferTo(File(location).also(File::mkdirs))", "java.io.File", "java.io.File"))
    override fun transferTo(location: String) = transferTo(File(location).also(File::mkdirs))

    override fun transferTo(destination: File) = Flowable.fromCallable { file.copyTo(destination, true).exists() }

    override fun transferTo(outputStream: OutputStream) = Flowable.fromCallable {
        file.inputStream().transferTo(outputStream) != 0L
    }

    override fun delete() = Flowable.just(file.deleteRecursively())
}
