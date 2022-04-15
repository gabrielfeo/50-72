/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package builder

internal data class PlainTextCommitMessageBuilder(
    private val commentChar: Char,
    private val sequence: FormattedCharSequence = FormattedCharSequence(),
) : CommitMessageBuilder {

    override fun build() = sequence.toString()

    override fun appendSubject(subject: String) {
        sequence.appendRaw(subject)
        sequence.breakParagraph()
    }

    override fun appendBody(body: String) {
        for (rawLine in body.lines()) {
            val line = rawLine.trim(' ')
            when {
                line.isEmpty() -> sequence.breakParagraph()
                line.isComment() -> continue
                else -> sequence.append(line)
            }
        }
    }

    private fun String.isComment() = startsWith(commentChar)
}
