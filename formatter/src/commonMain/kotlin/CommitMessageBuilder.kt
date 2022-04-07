import MarkdownTokenMatcher.*

internal inline fun buildPlainTextMessage(block: CommitMessageBuilder.() -> Unit): String =
    PlainTextCommitMessageBuilder().apply(block).build()

internal inline fun buildMarkdownMessage(block: CommitMessageBuilder.() -> Unit): String =
    MarkdownCommitMessageBuilder().apply(block).build()

interface CommitMessageBuilder {
    fun appendSubject(subject: String)
    fun appendBody(
        body: String,
        stripComments: Boolean,
    )
    fun build(): String
}

internal data class PlainTextCommitMessageBuilder(
    private val sequence: FormattedCharSequence = FormattedCharSequence(),
) : CommitMessageBuilder {

    override fun build() = sequence.toString()

    override fun appendSubject(subject: String) {
        sequence.appendRaw(subject)
        sequence.breakParagraph()
    }

    override fun appendBody(
        body: String,
        stripComments: Boolean,
    ) {
        body.lineSequence()
            .map { it.trim(' ') }
            .forEach {
                when {
                    it.isNotEmpty() && it.isNotComment() -> sequence.append(it)
                    it.isEmpty() -> sequence.breakParagraph()
                    it.isComment() -> when {
                        stripComments -> return@forEach
                        else -> sequence.appendRaw(it)
                    }
                    else -> error("Unpredicted case: line '$it'")
                }
            }
    }

    private fun String.isComment() = startsWith("#")
    private fun String.isNotComment() = !isComment()
}

internal data class MarkdownCommitMessageBuilder(
    private val sequence: FormattedCharSequence = FormattedCharSequence(),
) : CommitMessageBuilder {

    override fun build() = sequence.toString()

    override fun appendSubject(subject: String) {
        sequence.appendRaw(subject)
        sequence.breakParagraph()
    }

    override fun appendBody(
        body: String,
        stripComments: Boolean,
    ) {
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
                SingleLineBreak -> {}
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

internal data class FormattedCharSequence(
    private val builder: StringBuilder = StringBuilder(),
) : CharSequence by builder {

    private var currentLineLength: Int = 0
    private var hasPendingParagraphBreak = false

    override fun toString() = builder.toString()

    /**
     * Append a paragraph break to the message. The actual break is delayed until more content is
     * added. Will be discarded if
     * - no more content is appended to the message
     * - another break was added just before. It'd be a "double" paragraph break, which git would
     *   discard anyway.
     */
    fun breakParagraph() {
        // Only break if more content is appended
        hasPendingParagraphBreak = true
    }

    /*
    * Note to self:
    *   1. Regexp matching words and md literals (not as separators but as matches)
    *   2. If it gets unwieldy, use a simple startsWith on each word to check for the
    *      start markup of a literal. If it is, take words until the corresponding end
    *      markup comes up
    *
    * It did. '\*\*[\w \n]+\*\*|_[\w \n]+_|~[\w \n]+~|<[\w=" \n]+\/>|\w+' not even
    * matching punctuation and apostrophes yet. Countless corner cases.
    * */

    /**
     * Append content to the message breaking lines to respect the 72-column limit and trimming
     * redundant spaces.
     */
    fun append(value: CharSequence) {
        doPendingParagraphBreakOrReturn()
        for (word in value.split(Regex(" +"))) {
            val wordIsUpTo72 = word.length <= 72
            when {
                currentLineIsEmpty() -> appendRaw(word)
                wordIsUpTo72 && wouldLineStayUpTo72(word.length + 1) -> appendRaw(" $word")
                else -> appendRaw("\n$word")
            }
        }
    }

    /**
     * Append content to the message as-is, unlike [append].
     */
    fun appendRaw(value: CharSequence) {
        doPendingParagraphBreakOrReturn()
        builder.append(value)
        val indexOfLastLineBreakInValue = value.lastIndexOf('\n')
        val hasLineBreak = indexOfLastLineBreakInValue != -1
        if (hasLineBreak) {
            val lengthAfterLineBreak = value.length - indexOfLastLineBreakInValue - 1
            currentLineLength = lengthAfterLineBreak
        } else {
            currentLineLength += value.length
        }
    }

    private fun doPendingParagraphBreakOrReturn() {
        if (!hasPendingParagraphBreak || wouldNewParagraphBreakBeDoubleBreak()) {
            return
        }
        builder.append('\n')
        builder.append('\n')
        currentLineLength = 0
        hasPendingParagraphBreak = false
    }

    private fun wouldNewParagraphBreakBeDoubleBreak(): Boolean {
        return currentLineIsEmpty() && builder.takeLast(2) == "\n\n"
    }

    private fun wouldLineStayUpTo72(addedLength: Int) = currentLineLength + addedLength < 72
    private fun currentLineIsEmpty() = currentLineLength == 0
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
