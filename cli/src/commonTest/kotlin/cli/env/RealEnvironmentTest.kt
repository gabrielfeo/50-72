/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.env

import cli.commons.CommandRunner
import cli.commons.CommandRunner.Result
import cli.commons.CommandRunner.StdOut
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

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
        assertFails {
            environment.gitCommentChar()
        }
    }

    @Test
    fun givenGetCommentCharExit0WithInvalidCharThenFailsWithMessage() {
        val commandRunner = givenGetCommentCharExit0(";;")
        val environment = RealEnvironment(commandRunner)
        val error = assertFails {
            environment.gitCommentChar()
        }
        assertEquals(INVALID_COMMENT_CHAR_ERROR, error.message)
    }

    private fun givenGetCommentCharExit1() = CommandRunner {
        Result(exitCode = 1, StdOut("\n"))
    }

    private fun givenGetCommentCharExit0(setChar: String) = CommandRunner {
        Result(exitCode = 0, StdOut("$setChar\n"))
    }

    private fun givenGetCommentCharExit127() = CommandRunner {
        Result(exitCode = 127, StdOut("git: command not found\n"))
    }
}