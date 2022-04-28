/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

 package validation

import HEADING_OVER_50_MESSAGE
import NO_SUBJECT_BODY_SEPARATOR_MESSAGE
import info.CommitMessageInfo
import shouldEqual
import kotlin.test.Test
import kotlin.test.assertFails

class CommitMessageValidatorTest {

    private val validator: CommitMessageValidator = RealCommitMessageValidator()

    private fun validate(text: String) = validator.validate(CommitMessageInfo(text))

    @Test
    fun failsSingleLineOver50() {
        val error = assertFails {
            validate("012345678901234567890123456789012345678901234567890")
        }
        error.message shouldEqual HEADING_OVER_50_MESSAGE
    }

    @Test
    fun passesSingleLineAt50() {
        validate("01234567890123456789012345678901234567890123456789")
    }

    @Test
    fun passesSingleLineUnder50() {
        validate("0123456789012345678901234567890123456789")
    }

    @Test
    fun failsSubjectLineOver50() {
        val error = assertFails {
            validate("""
                012345678901234567890123456789012345678901234567890

                012345678901234567890123456789012345678901234567890123456789012345678901
                012345678901234567890123456789012345678901234567890123456789012345678901
            """.trimIndent())
        }
        error.message shouldEqual HEADING_OVER_50_MESSAGE
    }

    @Test
    fun passesSubjectLineAt50() {
        validate(
            """
                01234567890123456789012345678901234567890123456789

                012345678901234567890123456789012345678901234567890123456789012345678901
                012345678901234567890123456789012345678901234567890123456789012345678901
            """.trimIndent()
        )
    }

    @Test
    fun passesSubjectLineUnder50() {
        validate(
            """
                0123456789012345678901234567890123456789

                012345678901234567890123456789012345678901234567890123456789012345678901
                012345678901234567890123456789012345678901234567890123456789012345678901
            """.trimIndent()
        )
    }

    @Test
    fun failsIfNoSubjectBodySeparator() {
        val error = assertFails {
            validate("a\na")
        }
        error.message shouldEqual NO_SUBJECT_BODY_SEPARATOR_MESSAGE
    }

    @Test
    fun passesIfSubjectBodySeparator() {
        validate("a\n\na")
    }

    @Test
    fun passesIfTrailingNewline() {
        validate("a\n")
        validate("a\n\nb\n")
    }

    @Test
    fun failsSubjectLineOver50WhenCommentLinesBeforeSubject() {
        val error = assertFails {
            validate(
                """
                    # This is a combination of 2 commits.
                    # This is the 1st commit message:

                    012345678901234567890123456789012345678901234567890

                    # This is the commit message #2:

                    b

                    # Please enter the commit message for your changes. Lines starting
                    # with '#' will be ignored, and an empty message aborts the commit.
                    ...
                """.trimIndent()
            )
        }
        error.message shouldEqual HEADING_OVER_50_MESSAGE
    }
}
