/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.command.format

import cli.env.Environment
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktConsole
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

private const val ERROR_MESSAGE = "formatOut"

class FormatMessageCommandTest {

    private var stdout = ""
    private var stderr = ""
    private val formatArgs = object {
        var message: String? = null
        var commentChar: Char? = null
        var isMarkdown: Boolean? = null
    }

    @Test
    fun givenFormatReturnsThenPrintsStdoutAndExit0() {
        run("message")
        assertEquals("message\n", stdout)
        assertTrue(stderr.isEmpty())
    }

    @Test
    fun givenFormatThrowsThenInterruptsAndPrintsMessage() {
        val error = assertFailsWith<PrintMessage> {
            run("message", formatThrows = true)
        }
        assertEquals(ERROR_MESSAGE, error.message)
        assertTrue(stdout.isEmpty())
    }

    @Test
    fun isMarkdownFalseByDefault() {
        run("message")
        assertEquals(false, formatArgs.isMarkdown)
    }

    @Test
    fun usesCommandArgsInFormat() {
        run("message", "--markdown")
        assertEquals(true, formatArgs.isMarkdown)
        assertEquals("message", formatArgs.message)
    }

    @Test
    fun usesEnvironmentCommentCharInFormat() {
        run("message", environmentCommentChar = ';')
        assertEquals(';', formatArgs.commentChar)
    }

    @Test
    fun acceptsArgumentOrdering1() {
        run("message", "--markdown")
        assertEquals(true, formatArgs.isMarkdown)
        assertEquals("message", formatArgs.message)
    }

    @Test
    fun acceptsArgumentOrdering2() {
        run("--markdown", "message")
        assertEquals(true, formatArgs.isMarkdown)
        assertEquals("message", formatArgs.message)
    }

    @Suppress("UNCHECKED_CAST")
    private fun run(
        vararg args: String,
        formatThrows: Boolean = false,
        environmentCommentChar: Char = '#',
    ) {
        FormatMessageCommand(
            env = object : Environment {
                override fun gitCommentChar() = environmentCommentChar
            },
            format = { message, commentChar, isMarkdown ->
                formatArgs.isMarkdown = isMarkdown
                formatArgs.commentChar = commentChar
                formatArgs.message = message
                if (formatThrows) throw IllegalArgumentException(ERROR_MESSAGE)
                else message
            }
        ).context {
            console = object : CliktConsole {
                override fun promptForLine(prompt: String, hideInput: Boolean) = TODO()
                override val lineSeparator = "\n"
                override fun print(text: String, error: Boolean) = when {
                    error -> stderr += text
                    else -> stdout += text
                }
            }
        }.parse(args as Array<String>)
    }
}