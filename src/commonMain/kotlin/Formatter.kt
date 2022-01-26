
internal const val HEADING_OVER_50_MESSAGE = """
    Heading line must not be over 50 columns. Please re-format the heading manually.
"""

internal const val NO_SUBJECT_BODY_SEPARATOR_MESSAGE = """
    There must be a blank line between subject and body. Please add the blank line!
"""

private const val WORD_SPACING = ' '
private const val WORD_SPACING_SIZE = 1
private const val SUBJECT_BODY_SEPARATOR = "\n\n"

class Formatter {

    fun format(message: String): String {
        val trimmed = message.trim()
        val indexOfFirstNewline = trimmed.indexOf('\n')
        fun String.subject() = slice(0 until indexOfFirstNewline).trim()
        fun String.body() = slice(indexOfFirstNewline + 2..lastIndex).trim()

        val hasBody = indexOfFirstNewline != -1
        val subjectIsUpTo50Columns = (hasBody && indexOfFirstNewline in 1..50) || trimmed.length <= 50
        val hasSubjectBodySeparator = hasBody && trimmed[indexOfFirstNewline + 1] == '\n'

        require(subjectIsUpTo50Columns) { HEADING_OVER_50_MESSAGE }
        if (!hasBody) {
            return trimmed
        }
        require(hasSubjectBodySeparator) { NO_SUBJECT_BODY_SEPARATOR_MESSAGE }
        return buildString {
            append(trimmed.subject())
            append(SUBJECT_BODY_SEPARATOR)
            val body = trimmed.body()
            appendBodyReformattedUpTo72Columns(body)
        }
    }

    private fun StringBuilder.appendBodyReformattedUpTo72Columns(body: String) { // TODO Capacity
        var currentLineColumns = 0
        fun append(str: String) {
            this.append(str)
            if (str.startsWith('\n')) currentLineColumns = str.length - 1
            else currentLineColumns += str.length
        }
        for (word in body.split(Regex("\\s"))) {
            when {
                this.isEmpty() || word.length >= 72 -> append(word)
                currentLineColumns + word.length + WORD_SPACING_SIZE <= 72 -> append("$WORD_SPACING$word")
                else -> append("\n$word")
            }
        }
    }
}
