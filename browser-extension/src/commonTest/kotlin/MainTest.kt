import browser.dom.Document
import browser.dom.Event
import browser.dom.HTMLButtonElement
import fixture.GitHubEditingPrBody
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.events.Event
import kotlin.test.*

class Main2Test : DomManipulationTest() {

    private val fakeFormat: (String, Char, Boolean) -> String = { _, _, _ -> "" }

    @Test
    fun addsFormatButton() {
        val document = getDocument().prepare()
        main2(document.testElement, fakeFormat)
        val button = assertNotNull(document.testElement.querySelector("button"))
        assertEquals("Format", button.innerHTML)
        assertIs<HTMLButtonElement>(button)
    }

    @Test
    fun callsFormatOnButtonClick() {
        val document = getDocument().prepare()
        var formatCalled = false
        main2(document.testElement, format = { _, _, _ -> formatCalled = true; "" })
        assertHasFormatButton().dispatchEvent(Event("click"))
        assertTrue(formatCalled)
    }

    @Test
    fun givenIsEditingPrThenFormatsTextFromPrBody() {
        val document = getDocument().prepare(GitHubEditingPrBody("My body"))
        var textToFormat: String? = null
//        main(document.testElement, format = { text, _, _ -> textToFormat = text; "" })
        document.assertHasFormatButton().dispatchEvent(Event("click"))
        assertEquals("My Body", textToFormat)
    }

    private fun Document.assertHasFormatButton() = assertNotNull(testElement.querySelector("button"))
}
