/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

import kotlin.test.Test

class FormatMarkdownBodyTest {

    private fun formatBody(text: String) = formatBody(text, isMarkdown = true, commentChar = ';')

    @Test
    fun whenFormatBodyWithMarkdownOptionTrueThenFormatsAsMarkdown() {
        formatBody(
            """
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
                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

                ```kotlin
                println("snippet")
                println("snippet")
                ```
            """.trimIndent()
        )
    }

    @Test
    fun reformatsMarkdownBodyGivenParagraphLineOver72() {
        formatBody(
            """
                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

                foo foo

                ## H2

                foo foo
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
            """.trimIndent()
        ).shouldEqual(
            """
                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo

                foo foo

                ## H2

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo foo foo
            """.trimIndent()
        )
    }

    @Test
    fun returnsSameMessageGivenAllParagraphLinesAt72() {
        formatBody(
            """
                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

                ## H2

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
            """.trimIndent()
        ).shouldEqual(
            """
                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo

                ## H2

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
            """.trimIndent()
        )
    }

    @Test
    fun returnsSameMessageGivenAllParagraphLinesUpTo72() {
        formatBody(
            """
                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo fo

                foo foo foo

                ## H2

                foo foo foo
            """.trimIndent()
        ) shouldEqual(
            """
                # H1

                01234567890123456789012345678901234567890123456789012345678901234567890
                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo fo

                foo foo foo

                ## H2

                foo foo foo
            """.trimIndent()
        )
    }

    @Test
    fun doesntTouchListItems() {
        val original = """
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
        formatBody(original) shouldEqual original
    }

    @Test
    fun trimsRedundantWhitespaceBetweenParagraphs() {
        formatBody(
            """
                # H1

                foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                foo


                foo


                - A
                  - Nested



                1. B
                  a. Nested
            """.trimIndent()
        ).shouldEqual(
            """
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
        formatBody(
            """
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
            """.trimIndent()
        ).shouldEqual(
            """
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