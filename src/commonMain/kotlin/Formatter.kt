
internal const val HEADING_OVER_50_MESSAGE = """
    Heading line must not be over 50 columns. Please re-format the heading manually
"""

class Formatter {
    fun format(message: String): String {
        return when {
            message.length <= 50 -> message
            else -> error(HEADING_OVER_50_MESSAGE)
        }
    }
}