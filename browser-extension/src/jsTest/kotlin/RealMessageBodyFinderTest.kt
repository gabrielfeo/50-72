import fixture.GitHubEditingPrBody
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import kotlin.test.*

class RealMessageBodyFinderTest : DomManipulationTest() {

    @Test
    fun finds() {
        prepareDocument(GitHubEditingPrBody("My body"))
        val foundElement = RealMessageBodyFinder.find(testElement)
        assertIs<HTMLTextAreaElement>(foundElement)
    }
}
