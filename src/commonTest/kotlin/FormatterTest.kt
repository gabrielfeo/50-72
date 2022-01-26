import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

const val SINGLE_LINE_50 = "01234567890123456789012345678901234567890123456789"
const val SINGLE_LINE_51 = "012345678901234567890123456789012345678901234567890"

const val SUBJECT_51_BODY_72 = """$SINGLE_LINE_51

012345678901234567890123456789012345678901234567890123456789012345678901
012345678901234567890123456789012345678901234567890123456789012345678901"""

const val SUBJECT_50_BODY_72 = """$SINGLE_LINE_50

012345678901234567890123456789012345678901234567890123456789012345678901
012345678901234567890123456789012345678901234567890123456789012345678901"""

const val SUBJECT_50_BODY_73 = """$SINGLE_LINE_50

012345678901234567890123456789012345678901234567890123456789012345678901
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum"""

const val SUBJECT_50_BODY_73_FIXED = """$SINGLE_LINE_50

012345678901234567890123456789012345678901234567890123456789012345678901
lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem lorem
lorem lorem ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum ipsum
ipsum ipsum"""

class FormatterTest {

    private val formatter = Formatter()

    @Test
    fun failsGivenSingleLineOver50() {
        val error = assertFails {
            formatter.format(SINGLE_LINE_51)
        }
        assertEquals(HEADING_OVER_50_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenSingleLineUpTo50() {
        formatter.format(SINGLE_LINE_50)
    }

    @Test
    fun failsGivenSubjectLineOver50() {
        val error = assertFails {
            formatter.format(SUBJECT_51_BODY_72)
        }
        assertEquals(HEADING_OVER_50_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenSubjectLineUpTo50() {
        formatter.format(SUBJECT_50_BODY_72)
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

    @Test
    fun failsGivenBodyLineOver72() { // TODO Re-format instead
        assertFails {
            formatter.format(SUBJECT_50_BODY_73)
        }
    }

    @Test
    fun doesntFailGivenBodyLineUpTo72() {
        formatter.format(SUBJECT_50_BODY_72)
    }
}