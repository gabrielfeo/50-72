/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.env

import cli.commons.CommandRunner
import cli.commons.CommandRunner.Exit
import cli.commons.CommandRunner.StdOut
import com.github.ajalt.clikt.core.CliktError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RealEnvironmentTest {

    @Test
    fun givenGetCommentCharExit1ThenReturnsDefaultCommentChar() {
        val commandRunner = givenGetCommentCharExit1()
        val environment = RealEnvironment(commandRunner)
        assertEquals('#', environment.gitCommentChar())
    }

    @Test
    fun givenGetCommentCharExit0WithValidCharThenReturnsChar() {
        val commandRunner = givenGetCommentCharExit0(";")
        val environment = RealEnvironment(commandRunner)
        assertEquals(';', environment.gitCommentChar())
    }

    @Test
    fun givenGetCommentCharExit127ThenFails() {
        val commandRunner = givenGetCommentCharExit127()
        val environment = RealEnvironment(commandRunner)
        assertFailsWith(CliktError::class) {
            environment.gitCommentChar()
        }
    }

    @Test
    fun givenGetCommentCharExit0WithInvalidCharThenFailsWithMessage() {
        val commandRunner = givenGetCommentCharExit0(";;")
        val environment = RealEnvironment(commandRunner)
        val error = assertFailsWith(CliktError::class) {
            environment.gitCommentChar()
        }
        assertEquals(INVALID_COMMENT_CHAR_ERROR, error.message)
    }

    private fun givenGetCommentCharExit1() = CommandRunner {
        Exit(status = 1, StdOut("\n"))
    }

    private fun givenGetCommentCharExit0(setChar: String) = CommandRunner {
        Exit(status = 0, StdOut("$setChar\n"))
    }

    private fun givenGetCommentCharExit127() = CommandRunner {
        Exit(status = 127, StdOut("git: command not found\n"))
    }
}