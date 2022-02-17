import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.Document
import org.w3c.dom.HTMLTextAreaElement

const val SUPPORT_ADDRESS = "gabriel@gabrielfeo.com"

fun main() {
    val descriptionArea = findCommitMessageBodyTextArea()
    val description = descriptionArea?.value
    if (description?.isNotBlank() == true) {
        descriptionArea.value = formatBody(description)
    } else {
        alertFailedToFindDescriptionArea()
        logFailedToFindDescriptionArea(descriptionArea)
    }
}

private fun findCommitMessageBodyTextArea(): HTMLTextAreaElement? {
    val element = document.run { gitHubDescriptionArea ?: gitLabDescriptionArea }
    return element as? HTMLTextAreaElement
}

private val Document.gitHubDescriptionArea get() = querySelector("#pull_request_body")
private val Document.gitLabDescriptionArea get() = querySelector("#merge_request_description")

private fun alertFailedToFindDescriptionArea() {
    window.alert("""
        Couldn't find an PR/MR description area in this page :(
        
        Please report this to $SUPPORT_ADDRESS with:
          • the current page URL (please remove any sensitive data)
          • a screenshot of the page's Console (only the log entry starting with [50-72])
    """.trimIndent())
}

private fun logFailedToFindDescriptionArea(descriptionArea: HTMLTextAreaElement?) {
    console.log(
        """
            [50-72] Failed to format description.
              descriptionArea=$descriptionArea
                descriptionArea.textContent=${descriptionArea?.textContent} 
                descriptionArea.value=${descriptionArea?.value} 
        """.trimIndent()
    )
}
