
internal const val HEADING_OVER_50_MESSAGE = """
    Heading line must not be over 50 columns. Please re-format the heading manually.
"""

internal const val NO_SUBJECT_BODY_SEPARATOR_MESSAGE = """
    There must be a blank line between subject and body. Please add the blank line!
"""

class Formatter {
    fun format(message: String): String {
        val lines = message.split('\n')
        return when {
            lines.first().length in 1..50 -> when {
                lines.size == 1 || lines[1].isEmpty() -> message
                else -> error(NO_SUBJECT_BODY_SEPARATOR_MESSAGE)
            }
            else -> error(HEADING_OVER_50_MESSAGE)
        }
    }
}