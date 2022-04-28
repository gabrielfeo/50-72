/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import kotlin.test.Test

class FormatFullMessageMarkdownTest : FormatFullMessageTest() {

    override val isMarkdown = true

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
    fun doesntTouchListItems() {
        val original = """
            Subject

            # H1

            foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
            foo

            - List item A
            - List item B

            - List item A
            - List item B
            - List item C over 72 foo foo foo foo foo foo foo foo foo foo foo foo
            foo foo foo foo
            - List item D
            - List item E

            foo foo

            1. List item A
            2. List item B
            3. List item C over 72 foo foo foo foo foo foo foo foo foo foo foo foo
            foo foo foo foo
            4. List item D
            5. List item E

            foo

            - A
              - Nested
              - Nested over 72 foo foo foo foo foo foo foo foo foo foo foo foo foo foo
              - Nested

            1. B
              a. Nested
              b. Nested over 72 foo foo foo foo foo foo foo foo foo foo foo foo foo foo
              c. Nested

            ## H2

            foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
            foo foo foo
        """.trimIndent()
        formatFullMessage(original, commentChar = ';') shouldEqual original
    }

    @Test
    fun trimsRedundantWhitespaceBetweenParagraphs() {
        formatFullMessage(
            """
                Subject

                # H1

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo


                foo


                - A
                  - Nested



                1. B
                  a. Nested
            """.trimIndent(),
            commentChar = '%'
        ).shouldEqual(
            """
                Subject

                # H1

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo

                foo

                - A
                  - Nested

                1. B
                  a. Nested
            """.trimIndent()
        )
    }

    @Test
    fun supportsMiscMarkdownFeatures() {
        formatFullMessage(
            """
                Subject

                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

                ```
                snippety snip
                ```

                foo foo

                >Quote

                >Multi-line quote
                >Multi-line quote

                ## H2

                foo foo
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

                ### H3

                **Paragraph starting** with bold. Here's _italics_ and ~strikethrough~.

                #### H4

                <img src="foo" />

                ##### H5

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

                ![Screenshot](/uploads/foo.png)

                ###### H6

                ```
                pseudo code
                ```

                ```kotlin
                println("snippet")
                println("snippet")
                ```

                <details>
                <summary>
                Foo
                </summary>

                Bar

                </details>

                | Before | After |
                |--------|-------|
                | Foo    | Bar   |
            """.trimIndent(),
            commentChar = '%'
        ).shouldEqual(
            """
                Subject

                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo

                ```
                snippety snip
                ```

                foo foo

                >Quote

                >Multi-line quote
                >Multi-line quote

                ## H2

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo foo foo

                ### H3

                **Paragraph starting** with bold. Here's _italics_ and ~strikethrough~.

                #### H4

                <img src="foo" />

                ##### H5

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo

                ![Screenshot](/uploads/foo.png)

                ###### H6

                ```
                pseudo code
                ```

                ```kotlin
                println("snippet")
                println("snippet")
                ```

                <details>
                <summary>
                Foo
                </summary>

                Bar

                </details>

                | Before | After |
                |--------|-------|
                | Foo    | Bar   |
            """.trimIndent()
        )
    }
}
