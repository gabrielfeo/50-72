import kotlinx.browser.document
import kotlinx.dom.appendElement
import org.w3c.dom.Element

abstract class DomManipulationTest {

    protected val testElement: Element
        get() = checkNotNull(document.getElementById("test")) { "Test element not found" }

    protected open val testInnerHTML: String? = null

    protected fun prepareDocument(testInnerHTML: String = "") {
        document.getElementById("test")?.remove()
        checkNotNull(document.body).appendElement("div") {
            id = "test"
            innerHTML = testInnerHTML
        }
    }
}