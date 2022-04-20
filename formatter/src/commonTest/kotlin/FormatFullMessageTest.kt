/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class FormatFullMessageTest {

    @Test
    fun whenFormatFullMessageWithMarkdownOptionFalseThenFormatsAsPlainText() {
        val reformatted = formatFullMessage(MD_FULL_MSG_72_WITH_SNIPPET)
        assertEquals(MD_FULL_MSG_72_WITH_SNIPPET_FORMATTED_AS_PLAIN_TEXT, reformatted)
    }

    @Test
    fun failsGivenSingleLineOver50() {
        val error = assertFails {
            formatFullMessage(SINGLE_LINE_51)
        }
        assertEquals(HEADING_OVER_50_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenSingleLineAt50() {
        formatFullMessage(SINGLE_LINE_50)
    }

    @Test
    fun doesntFailGivenSingleLineUnder50() {
        formatFullMessage(SINGLE_LINE_40)
    }

    @Test
    fun failsGivenSubjectLineOver50() {
        val error = assertFails {
            formatFullMessage(SUBJECT_51_BODY_72)
        }
        assertEquals(HEADING_OVER_50_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenSubjectLineAt50() {
        formatFullMessage(SUBJECT_50_BODY_72)
    }

    @Test
    fun doesntFailGivenSubjectLineUnder50() {
        formatFullMessage(SUBJECT_40_BODY_72)
    }

    @Test
    fun failsGivenNoSubjectBodySeparator() {
        val error = assertFails {
            formatFullMessage("a\na")
        }
        assertEquals(NO_SUBJECT_BODY_SEPARATOR_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenNoSubjectBodySeparator() {
        formatFullMessage("a\n\na")
    }

    @Test
    fun reformatsBodyGivenBodyLineOver72() {
        val reformatted = formatFullMessage(SUBJECT_50_BODY_73)
        assertEquals(SUBJECT_50_BODY_73_FIXED, reformatted)
    }

    @Test
    fun doesntFailGivenBodyLineUnder72() {
        formatFullMessage(SUBJECT_50_BODY_71)
    }

    @Test
    fun doesntFailGivenBodyLineAt72() {
        formatFullMessage(SUBJECT_50_BODY_72)
    }

    @Test
    fun reformatsPreservingParagraphsWithSingleNewline() {
        val reformatted = formatFullMessage(SUBJECT_50_BODY_73_TWO_PARAGRAPHS)
        assertEquals(SUBJECT_50_BODY_73_TWO_PARAGRAPHS_FIXED, reformatted)
    }

    @Test
    fun reformatsIgnoringExcessiveNewlinesBetweenParagraphs() {
        val reformatted = formatFullMessage(SUBJECT_50_BODY_73_TWO_PARAGRAPHS_TRIPLE_NEWLINE)
        assertEquals(SUBJECT_50_BODY_73_TWO_PARAGRAPHS_TRIPLE_NEWLINE_FIXED, reformatted)
    }

    @Test
    fun stripsCommentsWithDefaultGitCommentChar() {
        val reformatted = formatFullMessage(MESSAGE_73_WITH_COMMENT_CHAR_HASH)
        assertEquals(MESSAGE_73_WITH_COMMENT_CHAR_HASH_FIXED, reformatted)
    }

    @Test
    fun stripsCommentsWithAlternativeGitCommentChar() {
        val reformatted = formatFullMessage(MESSAGE_73_WITH_COMMENT_CHAR_SEMICOLON, commentChar = ';')
        assertEquals(MESSAGE_73_WITH_COMMENT_CHAR_SEMICOLON_FIXED, reformatted)
    }

    /**
     * Git ignores any empty lines before the subject line, so it shouldn't be considered
     * for formatting or validation. Auto-generated squash messages are a case of this.
     */
    @Test
    fun stripsCommentsAndEmptyLinesBeforeSubject() {
        val reformatted = formatFullMessage(SQUASH_MESSAGE_SUBJECT_50_BODY_72)
        assertEquals(SQUASH_MESSAGE_SUBJECT_50_BODY_72_STRIPPED, reformatted)
    }

    @Test
    fun reformatsMiscMessages() {
        for ((i, case) in miscMessages.iterator().withIndex()) {
            val (original, expected) = case
            try {
                val actual = formatFullMessage(original)
                assertEquals(expected, actual, "Message differs at case $i")
            } catch (error: Throwable) {
                if (error is AssertionError) {
                    throw error
                }
                throw AssertionError("Exception thrown at case $i: ${error.stackTraceToString()}")
            }
        }
    }

    @Test
    fun reformatsMiscInvalidMessages() {
        for ((i, case) in miscInvalidMessages.iterator().withIndex()) {
            val (original, expectedErrorMessage) = case
            val error = assertFails {
                formatFullMessage(original)
            }
            assertEquals(expectedErrorMessage, error.message, "Error message differs at case $i")
        }
    }
}