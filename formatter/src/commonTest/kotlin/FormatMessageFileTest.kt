import kotlin.test.Test

class FormatMessageFileTest {

    @Test
    fun givenMessageFileWithNewlineAtEofThenDoesNotFail() {
        formatFullMessage("$SINGLE_LINE_40\n")
    }
}
