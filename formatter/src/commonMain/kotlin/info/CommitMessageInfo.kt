/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package info

import kotlin.text.RegexOption.MULTILINE

internal data class CommitMessageInfo(
    val fullText: String
) {

    private val indexOfFirstMessageChar: Int = run {
        val match = Regex("^[^#\n]", MULTILINE).find(fullText)
        checkNotNull(match?.range?.first)
    }

    private val indexOfFirstMessageNewline =
        fullText.indexOf('\n', startIndex = indexOfFirstMessageChar)

    private val firstNewlineIsNewlineAtEof =
        indexOfFirstMessageNewline == fullText.lastIndex

    val hasBody = indexOfFirstMessageNewline != -1 && !firstNewlineIsNewlineAtEof
    val hasSubjectBodySeparator = hasBody && fullText[indexOfFirstMessageNewline + 1] == '\n'
    val subjectIsUpTo50Columns: Boolean = run {
        val firstMessageChar = indexOfFirstMessageChar
        val hasBodyAndUpTo50 = hasBody
            && indexOfFirstMessageNewline in firstMessageChar..firstMessageChar + 50
        val noBodyAndUpTo50 = fullText.length <= firstMessageChar + 50
        hasBodyAndUpTo50 || noBodyAndUpTo50
    }

    fun subject() = fullText.slice(indexOfFirstMessageChar until indexOfFirstMessageNewline).trim()
    fun body() = fullText.slice(indexOfFirstMessageNewline + 2..fullText.lastIndex).trim()
}
