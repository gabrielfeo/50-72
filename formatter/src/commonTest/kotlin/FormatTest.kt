import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class FormatTest {

    @Test
    fun failsGivenSingleLineOver50() {
        val error = assertFails {
            format(SINGLE_LINE_51)
        }
        assertEquals(HEADING_OVER_50_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenSingleLineAt50() {
        format(SINGLE_LINE_50)
    }

    @Test
    fun doesntFailGivenSingleLineUnder50() {
        format(SINGLE_LINE_40)
    }

    @Test
    fun failsGivenSubjectLineOver50() {
        val error = assertFails {
            format(SUBJECT_51_BODY_72)
        }
        assertEquals(HEADING_OVER_50_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenSubjectLineAt50() {
        format(SUBJECT_50_BODY_72)
    }

    @Test
    fun doesntFailGivenSubjectLineUnder50() {
        format(SUBJECT_40_BODY_72)
    }

    @Test
    fun failsGivenNoSubjectBodySeparator() {
        val error = assertFails {
            format("a\na")
        }
        assertEquals(NO_SUBJECT_BODY_SEPARATOR_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenNoSubjectBodySeparator() {
        format("a\n\na")
    }

    @Test
    fun reformatsBodyGivenBodyLineOver72() {
        val reformatted = format(SUBJECT_50_BODY_73)
        assertEquals(SUBJECT_50_BODY_73_FIXED, reformatted)
    }

    @Test
    fun doesntFailGivenBodyLineUnder72() {
        format(SUBJECT_50_BODY_71)
    }

    @Test
    fun doesntFailGivenBodyLineAt72() {
        format(SUBJECT_50_BODY_72)
    }

    @Test
    fun reformatsMiscMessages() {
        miscMessages.onEachIndexed { i, (original, expected) ->
            val actual = format(original)
            assertEquals(expected, actual, "Failed at case $i")
        }
    }
}