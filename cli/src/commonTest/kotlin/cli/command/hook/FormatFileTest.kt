/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.command.hook

import cli.commons.readText
import cli.commons.writeText
import cli.env.Environment
import com.github.ajalt.clikt.core.PrintMessage
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.*

class FormatFileTest {

    private val fileSystem = FakeFileSystem().apply {
        emulateUnix()
    }

    private val formatArgs = object {
        var isMarkdown: Boolean? = null
        var commentChar: Char? = null
    }

    @Test
    fun givenMessageWithNoErrorsThenWritesFormattedMessage() {
        val file = "./msgfile"
        file.toPath().writeText("""Subject\n\nBody""", fileSystem)

        run(file, formatReturnValue = "formatted msg")

        assertEquals("formatted msg", file.toPath().readText(fileSystem))
    }

    @Test
    fun givenFormatFailsThenFailsWithFormatErrorMessage() {
        val file = "./msgfile"
        file.toPath().writeText("any", fileSystem)

        val error = assertFailsWith(PrintMessage::class) {
            run(file, formatErrorMessage = "123")
        }

        assertEquals("123", error.message)
        assertTrue(error.error)
    }

    @Test
    fun givenFormatFailsThenDoesntWriteToFile() {
        val file = "./msgfile"
        file.toPath().writeText("any", fileSystem)

        assertFails {
            run(file, formatErrorMessage = "any error")
        }

        assertEquals("any", file.toPath().readText(fileSystem))
    }

    @Test
    fun givenNoMessageArgumentThenUsesGitCommitMsgFile() {
        fileSystem.createDirectory(".git".toPath())
        DEFAULT_GIT_MSG_FILE.toPath().writeText("msg", fileSystem)

        run(formatReturnValue = "formatted msg")

        assertEquals("formatted msg", DEFAULT_GIT_MSG_FILE.toPath().readText(fileSystem))
    }

    @Test
    fun givenMarkdownNotSetThenFormatsAsPlainText() {
        val file = "./msgfile"
        file.toPath().writeText("msg", fileSystem)

        run(file)

        assertEquals(false, formatArgs.isMarkdown)
    }

    @Test
    fun givenMarkdownSetThenFormatsAsMarkdown() {
        val file = "./msgfile"
        file.toPath().writeText("msg", fileSystem)

        run(file, "--markdown")

        assertEquals(true, formatArgs.isMarkdown)
    }

    @Test
    fun usesEnvironmentCommentCharInFormat() {
        val file = "./msgfile"
        file.toPath().writeText("msg", fileSystem)

        run(file, environmentCommentChar = ';')

        assertEquals(';', formatArgs.commentChar)
    }

    @Suppress("UNCHECKED_CAST")
    private fun run(
        vararg args: String,
        formatReturnValue: String = "",
        formatErrorMessage: String? = null,
        environmentCommentChar: Char = '#',
    ) {
        FormatFile(
            fileSystem,
            env = object : Environment {
                override fun gitCommentChar() = environmentCommentChar
            },
            format = { _, commentChar, isMarkdown ->
                formatArgs.isMarkdown = isMarkdown
                formatArgs.commentChar = commentChar
                formatErrorMessage?.let { throw IllegalArgumentException(it) }
                    ?: formatReturnValue
            }
        ).parse(args as Array<String>)
    }
}
