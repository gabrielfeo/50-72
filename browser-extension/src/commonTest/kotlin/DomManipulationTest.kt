import browser.dom.Document
import browser.dom.Element
import browser.dom.appendElement

abstract class DomManipulationTest {

    protected val Document.testElement: Element
        get() = checkNotNull(getElementById("test")) { "Test element not found" }

    protected fun Document.prepare(testInnerHTML: String = ""): Document = this.apply {
        getElementById("test")?.remove()
        checkNotNull(body).appendElement("div") {
            id = "test"
            innerHTML = testInnerHTML
        }
    }
}

expect fun DomManipulationTest.getDocument(): Document
