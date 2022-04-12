import kotlinx.browser.document
import kotlinx.dom.appendElement
import org.w3c.dom.Element
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class Main2Test {

    private val testElement: Element
        get() = checkNotNull(document.getElementById("test")) { "Test element not found" }

    @Test
    fun addsFormatButton() {
        main2(testElement)
        assertNotNull(testElement.querySelector("button"))
    }

    @BeforeTest
    fun resetTestNode() {
        document.getElementById("test")?.remove()
        checkNotNull(document.body).appendElement("div") {
            id = "test"
        }
    }
}
