import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class FormatterTest {

    private val formatter = Formatter()

    @Test
    fun failsGivenSubjectLineOver50() {
        val error = assertFails {
            formatter.format(lineOf(51))
        }
        assertEquals(HEADING_OVER_50_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenSubjectLineUpTo50() {
        formatter.format(lineOf(50))
    }

    @Test
    fun failsGivenNoSubjectBodySeparator() {
        val error = assertFails {
            formatter.format("a\na")
        }
        assertEquals(NO_SUBJECT_BODY_SEPARATOR_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenNoSubjectBodySeparator() {
        formatter.format("a\n\na")
    }

    private fun lineOf(length: Int) = buildString {
        append(length)
        while (this.length < length) {
            append('a')
        }
    }
}