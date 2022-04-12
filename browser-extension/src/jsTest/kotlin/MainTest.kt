import kotlinx.browser.document
import kotlinx.dom.appendElement
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import kotlin.test.*

class Main2Test {

    private val testElement: Element
        get() = checkNotNull(document.getElementById("test")) { "Test element not found" }

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

    @BeforeTest
    fun resetTestNode() {
        document.getElementById("test")?.remove()
        checkNotNull(document.body).appendElement("div") {
            id = "test"
        }
    }
}
