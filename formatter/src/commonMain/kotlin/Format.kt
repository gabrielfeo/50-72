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
        appendBodyReformattedUpTo72Columns(message.body())
    }
}

fun formatBody(bodyText: String): String {
    return buildString {
        appendBodyReformattedUpTo72Columns(bodyText)
    }
}


private fun StringBuilder.appendBodyReformattedUpTo72Columns(body: String) { // TODO Capacity
    var currentLineColumns = 0
    fun append(str: String) {
        this.append(str)
        if ('\n' in str) currentLineColumns = str.substringAfterLast('\n').count { it != '\n' }
        else currentLineColumns += str.length
    }
    for (word in body.split(Regex("(?<=\\S)$WORD_SPACING*\\n(?!\\n)|$WORD_SPACING+"))) {
        when {
            currentLineColumns == 0 -> append(word)
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
