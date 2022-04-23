/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package builder

import builder.Matcher.Comment
import builder.Matcher.MarkdownToken.*

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
        val matchers = arrayOf(Comment(commentChar), *Matcher.MarkdownToken.values())
        var currentPosition = 0
        while (currentPosition < body.lastIndex) {
            val (matcher, match) = matchBodyPosition(currentPosition, body, matchers)
            when (matcher) {
                BoldText,
                ItalicizedText,
                Strikethrough,
                PlainText,
                Link -> sequence.append(match)
                BulletListItem,
                NumberedListItem,
                Table,
                CodeSnippet,
                HtmlElement,
                Quote,
                Heading,
                EndPunctuation -> sequence.appendRaw(match)
                StandalonePunctuation -> sequence.append(match.trim())
                ParagraphBreak -> sequence.breakParagraph()
                is Comment,
                WordSpacing,
                SingleLineBreak -> {
                }
            }
            currentPosition += match.length
        }
    }

    private fun matchBodyPosition(
        currentPosition: Int,
        body: String,
        matchers: Array<Matcher>,
    ): Pair<Matcher, String> {
        for (matcher in matchers) {
            val match = matcher.matchAt(body, currentPosition) ?: continue
            return matcher to match
        }
        val currentPositionSnippet = body.substring(currentPosition, currentPosition + 10)
        error("No match for position $currentPosition: $currentPositionSnippet...")
    }
}

private sealed interface Matcher {

    val pattern: String

    @OptIn(ExperimentalStdlibApi::class)
    fun matchAt(string: String, index: Int): String? =
        Regex(pattern).matchAt(string, index)?.value

    data class Comment(
        val commentChar: Char,
    ) : Matcher {
        //language=RegExp
        override val pattern = "$commentChar.*"
    }

    @Suppress("RegExpRedundantEscape") // Some are needed for JS
    enum class MarkdownToken(
        //language=RegExp
        override val pattern: String,
    ) : Matcher {
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
