/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

import info.DEFAULT_GIT_COMMENT_CHAR
import kotlin.test.Test
import kotlin.test.assertFails

class FormatFullMessageTest {

    private fun formatFullMessage(
        message: String,
        commentChar: Char = DEFAULT_GIT_COMMENT_CHAR,
    ) = formatFullMessage(
        message,
        commentChar,
        validator = {},
    )

    @Test
    fun whenFormatFullMessageWithMarkdownOptionFalseThenFormatsAsPlainText() {
        formatFullMessage(
            """
                01234567890123456789012345678901234567890123456789

                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

                ```kotlin
                println("snippet")
                println("snippet")
                ```
            """.trimIndent()
        ).shouldEqual(
            """
                01234567890123456789012345678901234567890123456789

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

                ```kotlin println("snippet") println("snippet") ```
            """.trimIndent()
        )
    }

    @Test
    fun validatesMessage() {
        var validated = false
        formatFullMessage(
            "any",
            validator = { validated = true }
        )
        validated shouldEqual true
    }

    @Test
    fun reformatsBodyGivenBodyLineOver72() {
        formatFullMessage(
            """
                01234567890123456789012345678901234567890123456789

                012345678901234567890123456789012345678901234567890123456789012345678901
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
            """.trimIndent()
        ) shouldEqual(
            """
                01234567890123456789012345678901234567890123456789
    
                012345678901234567890123456789012345678901234567890123456789012345678901
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum
            """.trimIndent()
        )
    }

    @Test
    fun doesntFailGivenBodyLineUnder72() {
        formatFullMessage(
            """
                01234567890123456789012345678901234567890123456789

                01234567890123456789012345678901234567890123456789012345678901234567890
            """.trimIndent()
        )
    }

    @Test
    fun doesntFailGivenBodyLineAt72() {
        formatFullMessage(
            """
                01234567890123456789012345678901234567890123456789

                012345678901234567890123456789012345678901234567890123456789012345678901
                012345678901234567890123456789012345678901234567890123456789012345678901
            """.trimIndent()
        )
    }

    @Test
    fun reformatsPreservingParagraphsWithSingleNewline() {
        formatFullMessage(
            """
                01234567890123456789012345678901234567890123456789

                012345678901234567890123456789012345678901234567890123456789012345678901
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem

                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum
            """.trimIndent()
        ) shouldEqual(
            """
                01234567890123456789012345678901234567890123456789
    
                012345678901234567890123456789012345678901234567890123456789012345678901
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem
    
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum ipsum ipsum
            """.trimIndent()
        )
    }

    @Test
    fun reformatsIgnoringExcessiveNewlinesBetweenParagraphs() {
        formatFullMessage(
            """
                01234567890123456789012345678901234567890123456789

                012345678901234567890123456789012345678901234567890123456789012345678901
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem


                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum
            """.trimIndent()
        ) shouldEqual(
            """
                01234567890123456789012345678901234567890123456789
    
                012345678901234567890123456789012345678901234567890123456789012345678901
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem
    
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum ipsum ipsum
            """.trimIndent()
        )
    }

    @Test
    fun reformatsMovingParenthesesAlongsideWords() {
        formatFullMessage(
            """
                |Subject
                |
                |foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo (foo foo)
                |foo
                |
                |foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo fo (foo)
                |foo
                |
                |In POSIX, to get the real status code WEXITSTATUS must be used with the
                |return of wait() (https://pubs.opengroup.org/onlinepubs/9699919799/functions/wait.html).
                |
                |In POSIX, to get the real status code WEXITSTATUS must be used with the
                |return of wait()(https://pubs.opengroup.org/onlinepubs/9699919799/functions/wait.html).
                |
            """.trimMargin()
        ) shouldEqual(
            """
                Subject

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                (foo foo) foo

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo fo
                (foo) foo

                In POSIX, to get the real status code WEXITSTATUS must be used with the
                return of wait()
                (https://pubs.opengroup.org/onlinepubs/9699919799/functions/wait.html).

                In POSIX, to get the real status code WEXITSTATUS must be used with the
                return of
                wait()(https://pubs.opengroup.org/onlinepubs/9699919799/functions/wait.html).
            """.trimIndent()
        )
    }

    @Test
    fun stripsCommentsWithDefaultGitCommentChar() {
        formatFullMessage(
            """
                Lorem ipsum

                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem
                #Useless comment

                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                # Another one
                lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum ipsum ipsum
                # Please enter the commit message for your changes. Lines starting
                # with '#' will be ignored, and an empty message aborts the commit.
            """.trimIndent()
        ) shouldEqual(
            """
                Lorem ipsum
    
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem
    
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum ipsum ipsum
            """.trimIndent()
        )
    }

    @Test
    fun stripsCommentsWithAlternativeGitCommentChar() {
        formatFullMessage(
            """
                Lorem ipsum

                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem
                ;Useless comment

                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                ; Another one
                lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum ipsum ipsum
                ; Please enter the commit message for your changes. Lines starting
                ; with '#' will be ignored, and an empty message aborts the commit.
            """.trimIndent(),
            commentChar = ';',
        ) shouldEqual(
            """
                Lorem ipsum
                
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem
                
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum ipsum ipsum
            """.trimIndent()
        )
    }

    /**
     * Git ignores any empty lines before the subject line, so it shouldn't be considered
     * for formatting or validation. Auto-generated squash messages are a case of this.
     */
    @Test
    fun stripsCommentsAndEmptyLinesBeforeSubject() {
        formatFullMessage(
            """
                |# This is a combination of 2 commits.
                |# This is the 1st commit message:
                |
                |a
                |
                |012345678901234567890123456789012345678901234567890
                |
                |# This is the commit message #2:
                |
                |012345678901234567890123456789012345678901234567890123456789012345678901
                |lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                |ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                |
                |# Please enter the commit message for your changes. Lines starting
                |# with '#' will be ignored, and an empty message aborts the commit.
                |#
                |# Date:      Fri Apr 1 11:21:10 2022 +0100
                |#
                |# interactive rebase in progress; onto 11e86f9
                |# Last commands done (2 commands done):
                |#    pick 019360b a
                |#    squash c442edd b
                |# No commands remaining.
                |# You are currently rebasing branch 'fix/error-on-squash-messages' on '11e86f9'.
                |#
                |# Changes to be committed:
                |#	new file:   a
                |#	new file:   b
                |#
                |
            """.trimMargin()
        ) shouldEqual(
            """
                a
                
                012345678901234567890123456789012345678901234567890
                
                012345678901234567890123456789012345678901234567890123456789012345678901
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum
            """.trimIndent()
        )
    }

    @Test
    fun stripsRedundantWhitespaceBetweenParagraphs() {
        formatFullMessage(
            """
                01234567890123456789012345678901234567890123456789
    
    
                foo
            """.trimIndent()
        ).shouldEqual(
            """
                01234567890123456789012345678901234567890123456789
                
                foo
            """.trimIndent()
        )
    }

    @Test
    fun stripsRedundantWhitespaceAtEndOfMessage() {
        formatFullMessage(
            """
                |01234567890123456789012345678901234567890123456789
                |
                |foo
                |
            """.trimMargin()
        ).shouldEqual(
            """
                01234567890123456789012345678901234567890123456789
                
                foo
            """.trimIndent()
        )
    }

    @Test
    fun stripsRedundantWhitespaceInsideParagraphs() {
        formatFullMessage(
            """
                01234567890123456789012345678901234567890123456789
                
                     foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo foo foo foo bar foo foo foo foo    foo foo foo foo bar foo foo foo foo foo foo foo 
                ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum 
            """.trimIndent()
        ).shouldEqual(
            """
                01234567890123456789012345678901234567890123456789
                
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo foo foo foo foo foo foo foo bar foo foo foo foo foo foo foo foo bar
                foo foo foo foo foo foo foo ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
            """.trimIndent()
        )
    }

    @Test
    fun formatsReallyTallMessages() {
        formatFullMessage(
            """
                01234567890123456789012345678901234567890123456789
                
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long long                
            """.trimIndent()
        ).shouldEqual(
            """
                01234567890123456789012345678901234567890123456789
                
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long
            """.trimIndent()
        )
    }

    @Test
    fun formatsReallyLargeMessages() {
        formatFullMessage(
            """
                01234567890123456789012345678901234567890123456789
                
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long long
            """.trimIndent()
        ).shouldEqual(
            """
                01234567890123456789012345678901234567890123456789
                
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long long long long long long long long long long long long long long
                long
            """.trimIndent()
        )
    }

    @Test
    fun doesNotFailForTrailingNewline() {
        formatFullMessage("0123456789012345678901234567890123456789\n")
        formatFullMessage("0123456789012345678901234567890123456789\n\nabc\n")
    }
}
