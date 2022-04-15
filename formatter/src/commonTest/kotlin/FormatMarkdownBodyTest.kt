/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

import kotlin.test.Test
import kotlin.test.assertEquals

class FormatMarkdownBodyTest {

    @Test
    fun whenFormatBodyWithMarkdownOptionTrueThenFormatsAsMarkdown() {
        val reformatted = formatBody(MD_BODY_72_WITH_SNIPPET, isMarkdown = true)
        assertEquals(MD_BODY_72_WITH_SNIPPET, reformatted)
    }

    @Test
    fun reformatsMarkdownBodyGivenParagraphLineOver72() {
        val reformatted = formatBody(MD_BODY_OVER_72, isMarkdown = true)
        assertEquals(MD_BODY_OVER_72_FIXED, reformatted)
    }

    @Test
    fun returnsSameMessageGivenAllParagraphLinesAt72() {
        val reformatted = formatBody(MD_BODY_72, isMarkdown = true)
        assertEquals(MD_BODY_72, reformatted)
    }

    @Test
    fun returnsSameMessageGivenAllParagraphLinesUpTo72() {
        val reformatted = formatBody(MD_BODY_71, isMarkdown = true)
        assertEquals(MD_BODY_71, reformatted)
    }

    @Test
    fun doesntTouchListItems() {
        val reformatted = formatBody(MD_BODY_AT_72_WITH_LIST_ITEMS, isMarkdown = true)
        assertEquals(MD_BODY_AT_72_WITH_LIST_ITEMS, reformatted)
    }

    @Test
    fun supportsMiscMarkdownFeatures() {
        val reformatted = formatBody(MD_BODY_OVER_72_WITH_MORE_MD_FEATURES, isMarkdown = true)
        assertEquals(MD_BODY_OVER_72_WITH_MORE_MD_FEATURES_FIXED, reformatted)
    }
}