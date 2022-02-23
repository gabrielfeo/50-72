import CommitMessage.MarkdownBody
import CommitMessage.MarkdownBody.Section.Other
import CommitMessage.MarkdownBody.Section.Paragraph
import kotlin.jvm.JvmInline

internal const val HEADING_OVER_50_MESSAGE = "Heading line must not be over 50 columns. Please re-format the heading manually."
internal const val NO_SUBJECT_BODY_SEPARATOR_MESSAGE = "There must be a blank line between subject and body. Please add the blank line!"

private const val WORD_SPACING = ' '
private const val WORD_SPACING_SIZE = 1
private const val SUBJECT_BODY_SEPARATOR = "\n\n"


fun formatFullMessage(messageText: String): String {
    val message = CommitMessage(messageText)
    require(message.subjectIsUpTo50Columns) { HEADING_OVER_50_MESSAGE }
    if (!message.hasBody) {
        return message.fullText
    }
    require(message.hasSubjectBodySeparator) { NO_SUBJECT_BODY_SEPARATOR_MESSAGE }
    return buildString {
        append(message.subject())
        append(SUBJECT_BODY_SEPARATOR)
        appendReformattedUpTo72Columns(message.body(), stripComments = true)
    }
}

fun formatBody(bodyText: String): String {
    return buildString {
        appendReformattedUpTo72Columns(bodyText, stripComments = true)
    }
}

fun formatMarkdownBody(bodyText: String): String {
    return buildString {
        val body = parseMarkdownBody(bodyText)
        for (section in body.sections) {
            if (this.isNotEmpty()) {
                append("\n\n")
            }
            when (section) {
                is Paragraph -> appendReformattedUpTo72Columns(section.content, stripComments = false)
                is Other -> append(section.content)
            }
        }
    }
}


private fun StringBuilder.appendReformattedUpTo72Columns(body: String, stripComments: Boolean) {
    val bodyContent = when {
        !stripComments -> body
        else -> body.lines()
            .filterNot { it.startsWith('#') }
            .joinToString("\n")
    }
    val words = bodyContent.split(Regex("(?<=\\S)$WORD_SPACING*\\n(?!\\n)|$WORD_SPACING+"))
    var currentLineColumns = 0
    fun append(str: String) {
        this.append(str)
        if ('\n' in str) currentLineColumns = str.substringAfterLast('\n').count { it != '\n' }
        else currentLineColumns += str.length
    }
    for (word in words) {
        when {
            currentLineColumns == 0 -> append(word)
            currentLineColumns + word.length + WORD_SPACING_SIZE <= 72 -> append("$WORD_SPACING$word")
            else -> append("\n$word")
        }
    }
}

@Suppress("RemoveExplicitTypeArguments")
private fun parseMarkdownBody(body: String): MarkdownBody {
    val sectionsRegex = Regex("""(?:[^\n]+\n?)+""", RegexOption.MULTILINE)
    val sections = sectionsRegex.findAll(body)
        .map { it.value }
        .map {
            if (it.endsWith('\n')) it.substring(0 until it.lastIndex)
            else it
        }
        .map {
            if (it.first().isLetterOrDigit()) Paragraph(it)
            else Other(it)
        }
    return MarkdownBody(sections)
}


private class CommitMessage(fullMessage: String) {
    val fullText = fullMessage.trim()
    private val indexOfFirstNewline = fullText.indexOf('\n')

    val hasBody = indexOfFirstNewline != -1
    val subjectIsUpTo50Columns = (hasBody && indexOfFirstNewline in 1..50) || fullText.length <= 50
    val hasSubjectBodySeparator = hasBody && fullText[indexOfFirstNewline + 1] == '\n'

    fun subject() = fullText.slice(0 until indexOfFirstNewline).trim()
    fun body() = fullText.slice(indexOfFirstNewline + 2..fullText.lastIndex).trim()

    @JvmInline
    value class MarkdownBody(val sections: Sequence<Section>) {

        sealed interface Section {

            @JvmInline
            value class Paragraph(val content: String) : Section

            @JvmInline
            value class Other(val content: String) : Section
        }
    }
}
