import kotlinx.browser.document
import kotlinx.dom.appendElement
import org.w3c.dom.Element
import kotlin.test.BeforeTest

abstract class DomManipulationTest {

    protected val testElement: Element
        get() = checkNotNull(document.getElementById("test")) { "Test element not found" }

    @BeforeTest
    fun resetTestNode() {
        document.getElementById("test")?.remove()
        checkNotNull(document.body).appendElement("div") {
            id = "test"
        }
    }
}