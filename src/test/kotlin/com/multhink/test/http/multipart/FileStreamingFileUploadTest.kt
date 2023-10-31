/*
 * Copyright (c) 2023 Krystian Piotr Kryszczak.
 * All rights reserved.
 */
package com.multhink.test.http.multipart

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.file.shouldHaveSameStructureAndContentAs
import io.kotest.matchers.shouldBe
import io.micronaut.http.MediaType
import io.reactivex.rxjava3.core.Flowable
import java.io.File
import java.nio.ByteBuffer

class FileStreamingFileUploadTest: FreeSpec({

    "streaming file upload attributes" - {
        "should be equals to the file" {
            // given
            val file = File("src/test/resources/media/image/java.png")

            // when
            val it = FileStreamingFileUpload(file)

            // then
            it.contentType.get() shouldBe MediaType.forFilename(file.name)
            it.name shouldBe file.name
            it.filename shouldBe file.name
            it.size shouldBe file.length()
            it.definedSize shouldBe file.length()
            it.isComplete shouldBe true
        }

        "should be equals to no exists file" {
            // given
            val file = File("no-exists-file")

            // when
            val it = FileStreamingFileUpload(file)

            // then
            it.contentType.get() shouldBe MediaType.TEXT_PLAIN_TYPE
            it.name shouldBe "no-exists-file"
            it.filename shouldBe "no-exists-file"
            it.size shouldBe 0L
            it.definedSize shouldBe 0L
            it.isComplete shouldBe false
        }
    }

    "streaming file upload subscribe" - {
        "should returns the file content or attributes" {
            // given
            val file = File("src/test/resources/media/image/java.png")

            // when
            val it = Flowable.fromPublisher(FileStreamingFileUpload(file))

            // then
            .test().await().values().first()
            it.inputStream.readAllBytes() shouldBe file.readBytes()
            it.bytes shouldBe file.readBytes()
            it.byteBuffer shouldBe ByteBuffer.wrap(file.readBytes())
            it.contentType.get() shouldBe MediaType.forFilename(file.name)
        }
    }

    "streaming file upload transfer to" - {
        "destination file should be successful" {
            // given
            val file = File("src/test/resources/media/image/java.png")
            val tempFile = File.createTempFile("${file.nameWithoutExtension}-", ".${file.extension}")

            // when
            FileStreamingFileUpload(file).transferTo(tempFile)

            // then
            .test().await()
            .assertComplete()
            .assertValue(true)

            tempFile shouldHaveSameStructureAndContentAs file
        }

        "output stream should be successful" {
            // given
            val file = File("src/test/resources/media/image/java.png")
            val tempFile = File.createTempFile("${file.nameWithoutExtension}-", ".${file.extension}")

            // when
            FileStreamingFileUpload(file).transferTo(tempFile.outputStream())

            // then
            .test().await()
            .assertComplete()
            .assertValue(true)

            tempFile shouldHaveSameStructureAndContentAs file
        }
    }

    "streaming file upload delete" - {
        "should be delete" {
            // given
            File("src/test/resources/media/image/java.png").let { file ->
                File.createTempFile("${file.nameWithoutExtension}-", ".${file.extension}").let {
                    file.copyTo(it, true)
                    FileStreamingFileUpload(it)
                }
            }

            // when
            .delete()

            // then
            .test()
            .awaitCount(1)
            .assertComplete()
            .assertValue(true)
        }
    }
})
