internal const val HEADING_OVER_50_MESSAGE = """
    Heading line must not be over 50 columns. Please re-format the heading manually.
"""

internal const val NO_SUBJECT_BODY_SEPARATOR_MESSAGE = """
    There must be a blank line between subject and body. Please add the blank line!
"""

private const val WORD_SPACING = ' '
private const val WORD_SPACING_SIZE = 1
private const val SUBJECT_BODY_SEPARATOR = "\n\n"


fun format(messageText: String): String {
    val message = CommitMessage(messageText)
    require(message.subjectIsUpTo50Columns) { HEADING_OVER_50_MESSAGE }
    if (!message.hasBody) {
        return message.fullText
    }
    require(message.hasSubjectBodySeparator) { NO_SUBJECT_BODY_SEPARATOR_MESSAGE }
    return buildString {
        append(message.subject())
        append(SUBJECT_BODY_SEPARATOR)
        appendBodyReformattedUpTo72Columns(message.body())
    }
}


private fun StringBuilder.appendBodyReformattedUpTo72Columns(body: String) { // TODO Capacity
    var currentLineColumns = 0
    fun append(str: String) {
        this.append(str)
        if (str.startsWith('\n')) currentLineColumns = str.length - 1
        else currentLineColumns += str.length
    }
    for (word in body.split(Regex("\\s+"))) {
        when {
            currentLineColumns == 0 || word.length >= 72 -> append(word)
            currentLineColumns + word.length + WORD_SPACING_SIZE <= 72 -> append("$WORD_SPACING$word")
            else -> append("\n$word")
        }
    }
}


private class CommitMessage(fullMessage: String) {
    val fullText = fullMessage.trim()
    private val indexOfFirstNewline = fullText.indexOf('\n')

    val hasBody = indexOfFirstNewline != -1
    val subjectIsUpTo50Columns = (hasBody && indexOfFirstNewline in 1..50) || fullText.length <= 50
    val hasSubjectBodySeparator = hasBody && fullText[indexOfFirstNewline + 1] == '\n'

    fun subject() = fullText.slice(0 until indexOfFirstNewline).trim()
    fun body() = fullText.slice(indexOfFirstNewline + 2..fullText.lastIndex).trim()
}
