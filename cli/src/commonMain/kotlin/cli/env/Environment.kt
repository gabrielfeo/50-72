/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.env

import cli.commons.CommandRunner

interface Environment {
    fun gitCommentChar(): Char
}

class RealEnvironment(
    private val commandRunner: CommandRunner,
) : Environment {

    override fun gitCommentChar(): Char {
        val (status, stdout) = commandRunner.run("git config --get core.commentChar")
        return when (status) {
            1 -> '#'
            0 -> checkIsValidCommentChar(stdout.content.trim())
            else -> errorGettingCommentChar(status)
        }
    }

    private fun errorGettingCommentChar(status: Int): Nothing {
        error("Failed to get git commentChar setting. Exit $status")
    }

    private fun checkIsValidCommentChar(string: String): Char {
        if (string.length > 1) {
            throw CliktError(INVALID_COMMENT_CHAR_ERROR)
        }
        return string.first()
    }
}

internal const val INVALID_COMMENT_CHAR_ERROR =
"""'error: core.commentChar should only be one character'
The current commentChar setting is invalid. Git will also give you this error
if you try to commit.

Change this setting to a single char, preferably something other than '#' so
that Git doesn't think Markdown headings are comment lines:

    git config core.commentChar ';'
"""
