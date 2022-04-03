/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import kotlin.text.RegexOption.MULTILINE

internal class CommitMessage(fullMessage: String) {
    val fullText = fullMessage.trim()

    private val indexOfFirstMessageChar: Int = run {
        val match = Regex("^[^#\n]", MULTILINE).find(fullText)
        checkNotNull(match?.range?.first)
    }
    private val indexOfFirstMessageNewline =
        fullText.indexOf('\n', startIndex = indexOfFirstMessageChar)

    val hasBody = indexOfFirstMessageNewline != -1
    val subjectIsUpTo50Columns: Boolean = run {
            val firstMessageChar = indexOfFirstMessageChar
            val hasBodyAndUpTo50 = hasBody
                && indexOfFirstMessageNewline in firstMessageChar..firstMessageChar + 50
            val noBodyAndUpTo50 = fullText.length <= firstMessageChar + 50
            hasBodyAndUpTo50 || noBodyAndUpTo50
        }
    val hasSubjectBodySeparator = hasBody && fullText[indexOfFirstMessageNewline + 1] == '\n'

    fun subject() = fullText.slice(indexOfFirstMessageChar until indexOfFirstMessageNewline).trim()
    fun body() = fullText.slice(indexOfFirstMessageNewline + 2..fullText.lastIndex).trim()
}
