/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package builder

import builder.TokenType.Comment
import builder.TokenType.MarkdownToken
import builder.TokenType.MarkdownToken.*

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
                EndPunctuation,
                -> sequence.appendRaw(token.text)
                StandalonePunctuation -> sequence.append(token.text.trim())
                ParagraphBreak -> sequence.breakParagraph()
                is Comment,
                WordSpacing,
                SingleLineBreak,
                -> {
                }
            }
            currentPosition += token.text.length
        }
    }
}

private data class Tokenizer(
    private val commentChar: Char,
) {

    private val tokenTypes = arrayOf(
        Comment(commentChar),
        *MarkdownToken.values(),
    )

    fun tokenizePosition(
        currentPosition: Int,
        body: String,
    ): Token {
        for (type in tokenTypes) {
            val match = type.matchAt(body, currentPosition) ?: continue
            return Token(type, match)
        }
        val currentPositionSnippet = body.substring(currentPosition, currentPosition + 10)
        error("No match for position $currentPosition: $currentPositionSnippet...")
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun TokenType.matchAt(
        string: String,
        index: Int,
    ): String? {
        val match = Regex(pattern).matchAt(string, index)
        return match?.value
    }

    data class Token(
        val type: TokenType,
        val text: String,
    )
}

private sealed interface TokenType {

    val pattern: String

    data class Comment(
        val commentChar: Char,
    ) : TokenType {
        //language=RegExp
        override val pattern = "$commentChar.*"
    }

    @Suppress("RegExpRedundantEscape") // Some are needed for JS
    enum class MarkdownToken(
        //language=RegExp
        override val pattern: String,
    ) : TokenType {
        ParagraphBreak("""\n\s*\n"""),
        Heading("""#+[^\n]+"""),
        BoldText("""\*\*[^*]+\*\*"""),
        ItalicizedText("""_[^_]+_"""),
        Strikethrough("""~[^~]+~"""),
        Quote(""">[^\n]+(?:\n>[^\n]+)*"""),
        Table("""\|.*\|(?:\n\|.*\|){2,}"""),
        BulletListItem("""[ \t]*- [^\n]+(?:\n[^\n]+|[ \t]*- [^\n]+)*"""),
        NumberedListItem("""[\t ]*\d{1,9}\. [^\n]+(?:\n[^\n]+|\n[\t ]*\d{1,9}\. [^\n]+)*"""),
        CodeSnippet("""```[^`]+```"""),
        HtmlElement("""<[^\/>]+\/>|<(\w+)[^\/>]*>[\s\S]*?<\/\1>"""),
        Link("""!?\[[^\]]+\][\[(][^\])]+[\])]"""),
        PlainText("""(?:\S*\w+\S*)+"""),
        StandalonePunctuation("""[\t ]+[^\w\s]+(?=\s)"""),
        WordSpacing(" +"),
        SingleLineBreak("""\n"""),
        EndPunctuation("""\s*[^\w\s](?=$|\n\n)"""),
    }
}
