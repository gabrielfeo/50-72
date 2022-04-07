/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package builder

internal data class PlainTextCommitMessageBuilder(
    private val sequence: FormattedCharSequence = FormattedCharSequence(),
) : CommitMessageBuilder {

    override fun build() = sequence.toString()

    override fun appendSubject(subject: String) {
        sequence.appendRaw(subject)
        sequence.breakParagraph()
    }

    override fun appendBody(body: String) {
        body.lineSequence()
            .map { it.trim(' ') }
            .forEach {
                when {
                    it.isNotEmpty() && it.isNotComment() -> sequence.append(it)
                    it.isEmpty() -> sequence.breakParagraph()
                    it.isComment() -> return@forEach
                    else -> error("Unpredicted case: line '$it'")
                }
            }
    }

    private fun String.isComment() = startsWith("#")
    private fun String.isNotComment() = !isComment()
}
