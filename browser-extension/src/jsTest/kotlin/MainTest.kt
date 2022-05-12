import org.w3c.dom.HTMLButtonElement
import kotlin.test.*

class Main2Test : DomManipulationTest() {

    private var formatCalled = false
    private val format: (String, Boolean) -> String = { _, _ ->
        formatCalled = true
        ""
    }

    @Test
    fun addsFormatButton() {
        main2(testElement)
        val button = assertNotNull(testElement.querySelector("button"))
        assertEquals("Format", button.innerHTML)
        assertIs<HTMLButtonElement>(button)
    }

    @Test
    fun formatsOnButtonClick() {
        main2(testElement, format)

        val button = assertIs<HTMLButtonElement>(testElement.querySelector("button"))
        button.click()

        assertTrue(formatCalled)
    }
}
