/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package builder

import builder.MarkdownTokenMatcher.*

internal data class MarkdownCommitMessageBuilder(
    private val stripComments: Boolean,
    private val sequence: FormattedCharSequence = FormattedCharSequence(),
) : CommitMessageBuilder {

    override fun build() = sequence.toString()

    override fun appendSubject(subject: String) {
        sequence.appendRaw(subject)
        sequence.breakParagraph()
    }

    override fun appendBody(body: String) {
        require(!stripComments) { "Unsupported" } // TODO Remove this
        val matchers = MarkdownTokenMatcher.values()
        var currentPosition = 0
        while (currentPosition < body.lastIndex) {
            val (matcher, match) = tokenizeMarkdownBodyPosition(currentPosition, body, matchers)
            when (matcher) {
                BoldText,
                ItalicizedText,
                Strikethrough,
                PlainText,
                Link -> sequence.append(match.value)
                BulletListItem,
                NumberedListItem,
                Table,
                CodeSnippet,
                HtmlElement,
                Quote,
                Heading,
                Punctuation -> sequence.appendRaw(match.value)
                ParagraphBreak -> sequence.breakParagraph()
                WordSpacing,
                SingleLineBreak -> {
                }
            }
            currentPosition = match.range.last + 1
        }
    }

    private fun tokenizeMarkdownBodyPosition(
        currentPosition: Int,
        body: String,
        matchers: Array<MarkdownTokenMatcher>,
    ): Pair<MarkdownTokenMatcher, MatchResult> {
        for (matcher in matchers) {
            val match = Regex(matcher.pattern).find(body, currentPosition)
            if (match?.range?.first == currentPosition) {
                return Pair(matcher, match)
            }
        }
        val currentPositionSnippet = body.substring(currentPosition, currentPosition + 10)
        error("No match for position $currentPosition: $currentPositionSnippet...")
    }
}

@Suppress("RegExpRedundantEscape") // Some are needed for JS
private enum class MarkdownTokenMatcher(
    //language=RegExp
    val pattern: String,
) {
    ParagraphBreak("""\n{2,}"""),
    Heading("""#+[^\n]+"""),
    BoldText("""\*\*[^*]+\*\*"""),
    ItalicizedText("""_[^_]+_"""),
    Strikethrough("""~[^~]+~"""),
    Quote(""">[^\n]*"""),
    Table("""\|.*\|(?:\n\|.*\|){2,}"""),
    BulletListItem("""- [^\n]+"""),
    NumberedListItem("""\d+\. [^\n]+"""),
    CodeSnippet("""```[^`]+```"""),
    HtmlElement("""<[^<>]+/?>"""),
    Link("""!?\[[^\]]+\][\[(][^\])]+[\])]"""),
    PlainText("""(?:\w+\S*)+"""),
    WordSpacing(" +"),
    SingleLineBreak("""\n"""),
    Punctuation("""[^\w\s]"""),
}
