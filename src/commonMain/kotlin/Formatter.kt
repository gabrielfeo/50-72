
internal const val HEADING_OVER_50_MESSAGE = """
    Heading line must not be over 50 columns. Please re-format the heading manually.
"""

internal const val NO_SUBJECT_BODY_SEPARATOR_MESSAGE = """
    There must be a blank line between subject and body. Please add the blank line!
"""

class Formatter {
    fun format(message: String): String {
        val trimmed = message.trim()
        val indexOfFirstNewline = trimmed.indexOf('\n')
        if (indexOfFirstNewline == -1) {
            if (trimmed.length <= 50) return trimmed
            else error(HEADING_OVER_50_MESSAGE)
        } else if (indexOfFirstNewline > 50) {
            error(HEADING_OVER_50_MESSAGE)
        } else {
            if (trimmed[indexOfFirstNewline + 1] != '\n') {
                error(NO_SUBJECT_BODY_SEPARATOR_MESSAGE)
            } else {
                val bodyLines = trimmed.slice(53..trimmed.lastIndex).split('\n')
                if (bodyLines.any { it.length > 72 }) error("")
                else return trimmed
            }
        }
    }
}
