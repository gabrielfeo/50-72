import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class FormatFullMessageTest {

    @Test
    fun failsGivenSingleLineOver50() {
        val error = assertFails {
            formatFullMessage(SINGLE_LINE_51)
        }
        assertEquals(HEADING_OVER_50_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenSingleLineAt50() {
        formatFullMessage(SINGLE_LINE_50)
    }

    @Test
    fun doesntFailGivenSingleLineUnder50() {
        formatFullMessage(SINGLE_LINE_40)
    }

    @Test
    fun failsGivenSubjectLineOver50() {
        val error = assertFails {
            formatFullMessage(SUBJECT_51_BODY_72)
        }
        assertEquals(HEADING_OVER_50_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenSubjectLineAt50() {
        formatFullMessage(SUBJECT_50_BODY_72)
    }

    @Test
    fun doesntFailGivenSubjectLineUnder50() {
        formatFullMessage(SUBJECT_40_BODY_72)
    }

    @Test
    fun failsGivenNoSubjectBodySeparator() {
        val error = assertFails {
            formatFullMessage("a\na")
        }
        assertEquals(NO_SUBJECT_BODY_SEPARATOR_MESSAGE, error.message)
    }

    @Test
    fun doesntFailGivenNoSubjectBodySeparator() {
        formatFullMessage("a\n\na")
    }

    @Test
    fun reformatsBodyGivenBodyLineOver72() {
        val reformatted = formatFullMessage(SUBJECT_50_BODY_73)
        assertEquals(SUBJECT_50_BODY_73_FIXED, reformatted)
    }

    @Test
    fun doesntFailGivenBodyLineUnder72() {
        formatFullMessage(SUBJECT_50_BODY_71)
    }

    @Test
    fun doesntFailGivenBodyLineAt72() {
        formatFullMessage(SUBJECT_50_BODY_72)
    }

    @Test
    fun reformatsPreservingParagraphsWithSingleNewline() {
        val reformatted = formatFullMessage(SUBJECT_50_BODY_73_TWO_PARAGRAPHS)
        assertEquals(SUBJECT_50_BODY_73_TWO_PARAGRAPHS_FIXED, reformatted)
    }

    @Test
    fun reformatsPreservingParagraphsWithMultipleNewlines() {
        val reformatted = formatFullMessage(SUBJECT_50_BODY_73_TWO_PARAGRAPHS_DOUBLE_NEWLINE)
        assertEquals(SUBJECT_50_BODY_73_TWO_PARAGRAPHS_DOUBLE_NEWLINE_FIXED, reformatted)
    }

    @Test
    fun stripsMessageComments() {
        val reformatted = formatFullMessage(MESSAGE_73_WITH_COMMENTS)
        assertEquals(MESSAGE_73_WITH_COMMENTS_FIXED, reformatted)
    }

    @Test
    fun reformatsMiscMessages() {
        miscMessages.onEachIndexed { i, (original, expected) ->
            val actual = formatFullMessage(original)
            assertEquals(expected, actual, "Failed at case $i")
        }
    }
}