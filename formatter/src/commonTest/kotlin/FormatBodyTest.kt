import kotlin.test.Test
import kotlin.test.assertEquals

class FormatBodyTest {

    @Test
    fun reformatsBodyGivenBodyLineOver72() {
        val reformatted = formatBody(BODY_73)
        assertEquals(BODY_73_FIXED, reformatted)
    }

    @Test
    fun doesntFailGivenBodyLinesAt72() {
        val reformatted = formatBody(BODY_72)
        assertEquals(BODY_72, reformatted)
    }

    @Test
    fun doesntFailGivenBodyLineUnder72() {
        val reformatted = formatBody(BODY_71)
        assertEquals(BODY_71, reformatted)
    }
}