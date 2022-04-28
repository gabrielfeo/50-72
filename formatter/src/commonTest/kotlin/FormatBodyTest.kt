/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import info.DEFAULT_GIT_COMMENT_CHAR
import kotlin.test.Test

abstract class FormatBodyTest {

    abstract val isMarkdown: Boolean

    protected fun formatBody(
        message: String,
        commentChar: Char = DEFAULT_GIT_COMMENT_CHAR,
    ) = formatBody(
        message,
        commentChar,
        isMarkdown,
    )

    @Test
    fun reformatsBodyGivenBodyLineOver72() {
        formatBody(
            """
                012345678901234567890123456789012345678901234567890123456789012345678901
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
            """.trimIndent()
        ) shouldEqual(
            """
                012345678901234567890123456789012345678901234567890123456789012345678901
                lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
                lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
                ipsum ipsum
            """.trimIndent()
        )
    }

    @Test
    fun doesntFailGivenBodyLinesAt72() {
        formatBody(
            """
                012345678901234567890123456789012345678901234567890123456789012345678901
                012345678901234567890123456789012345678901234567890123456789012345678901
            """.trimIndent()
        ) shouldEqual(
            """
                012345678901234567890123456789012345678901234567890123456789012345678901
                012345678901234567890123456789012345678901234567890123456789012345678901
            """.trimIndent()
        )
    }

    @Test
    fun doesntFailGivenBodyLineUnder72() {
        formatBody(
            "01234567890123456789012345678901234567890123456789012345678901234567890"
        ) shouldEqual
            "01234567890123456789012345678901234567890123456789012345678901234567890"
    }
}
