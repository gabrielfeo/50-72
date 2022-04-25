/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import info.DEFAULT_GIT_COMMENT_CHAR
import kotlin.test.Test

class FormatMarkdownFullMessageTest {

    @Test
    fun whenFormatFullMessageWithMarkdownOptionTrueThenFormatsAsMarkdown() {
        val original = """
            01234567890123456789012345678901234567890123456789

            # H1

            01234567890123456789012345678901234567890123456789012345678901234567890
            foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

            ```kotlin
            println("snippet")
            println("snippet")
            ```
        """.trimIndent()
        val formatted = formatFullMessage(original, isMarkdown = true, commentChar = ';')
        formatted shouldEqual original
    }

    @Test
    fun stripsCommentsWithDefaultGitCommentChar() {
        formatFullMessage(
            """
                Subject

                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo

                foo foo

                ## H2

                #Comment

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo foo foo
                # Comment
            """.trimIndent(),
            isMarkdown = true,
            commentChar = DEFAULT_GIT_COMMENT_CHAR,
        ).shouldEqual(
            """
                Subject

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo

                foo foo

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo foo foo
            """.trimIndent()
        )
    }

    @Test
    fun stripsCommentsWithAlternativeGitCommentChar() {
        formatFullMessage(
            """
                Subject

                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo

                ## H2

                ;Comment

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo foo foo
                ; Comment
            """.trimIndent(),
            isMarkdown = true,
            commentChar = ';',
        ).shouldEqual(
            """
                Subject

                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo

                ## H2

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo foo foo
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
            """.trimMargin(),
            isMarkdown = true,
            commentChar = ';',
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
    fun reformatsCorrectlyWithMiscPunctuation() {
        formatFullMessage(
            """
                [JIRA-1] (subject): bla bla. Bla (bla...) or 'bla'

                bla. 1bla `bla`; bla\bla bla/bla bla+bla bla+ bla++ --bla -bla -b. "bla"? bla@bla bla?! US${'$'} 40.00 | 2¢ {£4} ~3%

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo a && b

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo a || b

                *alsm bla.

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo Closes issue #33

                bla bla ( bla )

                alsm bla?
            """.trimIndent(),
            isMarkdown = true,
            commentChar = ';',
        ).shouldEqual(
            """
                [JIRA-1] (subject): bla bla. Bla (bla...) or 'bla'

                bla. 1bla `bla`; bla\bla bla/bla bla+bla bla+ bla++ --bla -bla -b.
                "bla"? bla@bla bla?! US${'$'} 40.00 | 2¢ {£4} ~3%

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo a
                && b

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo a
                || b

                *alsm bla.

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo Closes issue
                #33

                bla bla ( bla )

                alsm bla?
            """.trimIndent()
        )
    }
}
