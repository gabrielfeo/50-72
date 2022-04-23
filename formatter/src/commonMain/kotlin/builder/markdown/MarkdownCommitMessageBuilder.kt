/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package builder.markdown

import builder.CommitMessageBuilder
import builder.FormattedCharSequence
import builder.markdown.TokenType.GitComment
import builder.markdown.TokenType.MarkdownToken.*

internal data class MarkdownCommitMessageBuilder(
    private val commentChar: Char,
    private val sequence: FormattedCharSequence = FormattedCharSequence(),
) : CommitMessageBuilder {

    override fun build() = sequence.toString()

    override fun appendSubject(subject: String) {
        sequence.appendRaw(subject)
        sequence.breakParagraph()
    }

    @Suppress("RemoveRedundantQualifierName")
    override fun appendBody(body: String) {
        val tokenizer = Tokenizer(commentChar)
        var currentPosition = 0
        while (currentPosition < body.lastIndex) {
            val token = tokenizer.tokenizePosition(currentPosition, body)
            when (token.type) {
                BoldText,
                ItalicizedText,
                Strikethrough,
                PlainText,
                Link,
                -> sequence.append(token.text)
                BulletListItem,
                NumberedListItem,
                Table,
                CodeSnippet,
                HtmlElement,
                Quote,
                Heading,
                EndPunctuation -> sequence.appendRaw(token.text)
                StandalonePunctuation -> sequence.append(token.text.trim())
                ParagraphBreak -> sequence.breakParagraph()
                is GitComment,
                WordSpacing,
                SingleLineBreak -> {
                }
            }
            currentPosition += token.text.length
        }
    }
}
