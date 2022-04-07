/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package builder

internal interface CommitMessageBuilder {
    fun appendSubject(subject: String)
    fun appendBody(body: String)
    fun build(): String
}

internal inline fun buildMessage(
    isMarkdown: Boolean = false,
    block: CommitMessageBuilder.() -> Unit
): String {
    val stripComments = !isMarkdown
    val builder = when {
        isMarkdown -> MarkdownCommitMessageBuilder(stripComments)
        else -> PlainTextCommitMessageBuilder(stripComments)
    }
    return builder.apply(block).build()
}
