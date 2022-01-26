
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
        fun String.body() = slice(indexOfFirstNewline + 2..lastIndex)

        val hasBody = indexOfFirstNewline != -1
        val subjectIsUpTo50Columns = (hasBody && indexOfFirstNewline in 1..50) || trimmed.length <= 50
        val hasSubjectBodySeparator = hasBody && trimmed[indexOfFirstNewline + 1] == '\n'

        require(subjectIsUpTo50Columns) { HEADING_OVER_50_MESSAGE }
        if (hasBody) {
            require(hasSubjectBodySeparator) { NO_SUBJECT_BODY_SEPARATOR_MESSAGE }
            checkBodyLinesUpTo72(trimmed.body())
        }
        return trimmed
    }

    private fun checkBodyLinesUpTo72(body: String) {
        val bodyLines = body.split('\n')
        require(bodyLines.none { it.length > 72 })
    }
}
