/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import info.DEFAULT_GIT_COMMENT_CHAR
import kotlin.test.Test
import kotlin.test.assertEquals

class FormatMarkdownFullMessageTest {

    @Test
    fun whenFormatFullMessageWithMarkdownOptionTrueThenFormatsAsMarkdown() {
        val reformatted = formatFullMessage(
            MD_FULL_MSG_72_WITH_SNIPPET,
            isMarkdown = true,
            commentChar = ';',
        )
        assertEquals(MD_FULL_MSG_72_WITH_SNIPPET, reformatted)
    }

    @Test
    fun stripsCommentsWithDefaultGitCommentChar() {
        val reformatted = formatFullMessage(
            MD_MSG_WITH_COMMENT_CHAR_HASH,
            isMarkdown = true,
            commentChar = DEFAULT_GIT_COMMENT_CHAR,
        )
        assertEquals(MD_MSG_WITH_COMMENT_CHAR_HASH_STRIPPED, reformatted)
    }

    @Test
    fun stripsCommentsWithAlternativeGitCommentChar() {
        val reformatted = formatFullMessage(
            MD_MSG_WITH_COMMENT_CHAR_SEMICOLON,
            isMarkdown = true,
            commentChar = ';',
        )
        assertEquals(MD_MSG_WITH_COMMENT_CHAR_SEMICOLON_STRIPPED, reformatted)
    }

    @Test
    fun reformatsMovingParenthesesAlongsideWords() {
        val reformatted = formatFullMessage(
            MESSAGE_WITH_PARENTHESIZED_WORDS_ON_72_COLUMN,
            isMarkdown = true,
            commentChar = ';',
        )
        assertEquals(MESSAGE_WITH_PARENTHESIZED_WORDS_ON_72_COLUMN_FIXED, reformatted)
    }

    @Test
    fun reformatsCorrectlyWithMiscPunctuation() {
        val reformatted = formatFullMessage(
            MD_BODY_WITH_MISC_PUNCTUATION,
            isMarkdown = true,
            commentChar = ';',
        )
        assertEquals(MD_BODY_WITH_MISC_PUNCTUATION_FIXED, reformatted)
    }
}
