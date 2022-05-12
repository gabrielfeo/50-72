import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.events.Event
import kotlin.test.*

class Main2Test : DomManipulationTest() {

    private val fakeFormat: (String, Char, Boolean) -> String = { _, _, _ -> "" }

    @Test
    fun addsFormatButton() {
        main2(testElement, fakeFormat)
        val button = assertNotNull(testElement.querySelector("button"))
        assertEquals("Format", button.innerHTML)
        assertIs<HTMLButtonElement>(button)
    }

    @Test
    fun callsFormatOnButtonClick() {
        var formatCalled = false
        main2(testElement, format = { _, _, _ -> formatCalled = true; "" })
        requireFormatButton().dispatchEvent(Event("click"))
        assertTrue(formatCalled)
    }

    private fun requireFormatButton() = assertNotNull(testElement.querySelector("button"))
}
