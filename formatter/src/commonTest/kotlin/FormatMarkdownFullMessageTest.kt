/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import kotlin.test.Test
import kotlin.test.assertEquals

class FormatMarkdownFullMessageTest {

    @Test
    fun whenFormatFullMessageWithMarkdownOptionTrueThenFormatsAsMarkdown() {
        val reformatted = formatFullMessage(MD_FULL_MSG_72_WITH_SNIPPET, isMarkdown = true)
        assertEquals(MD_FULL_MSG_72_WITH_SNIPPET, reformatted)
    }
}
