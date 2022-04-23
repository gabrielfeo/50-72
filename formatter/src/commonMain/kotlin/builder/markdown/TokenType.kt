/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package builder.markdown

sealed interface TokenType {

    val pattern: String

    data class GitComment(
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
