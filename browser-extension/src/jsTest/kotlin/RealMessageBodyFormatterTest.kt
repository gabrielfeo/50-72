import kotlinx.browser.document
import org.w3c.dom.HTMLTextAreaElement
import kotlin.test.Test
import kotlin.test.assertEquals

class RealMessageBodyFormatterTest : DomManipulationTest() {

    @Test
    fun givenIsEditingPrThenFormatsTextFromPrBody() {
        prepareDocument(
            """
                <textarea>My body</textarea>
            """.trimIndent()
        )
        val area = checkNotNull(document.querySelector("textarea") as? HTMLTextAreaElement)
        var textToFormat: String? = null
        RealMessageBodyFormatter.format(area, format = { text, _, _ -> textToFormat = text; "" })
        assertEquals("My body", textToFormat)
    }
}
