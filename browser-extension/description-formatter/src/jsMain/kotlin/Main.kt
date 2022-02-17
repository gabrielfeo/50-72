import kotlinx.browser.document
import org.w3c.dom.Document
import org.w3c.dom.HTMLTextAreaElement

fun main() {
    val descriptionArea = findCommitMessageBodyTextArea()
    val description = descriptionArea?.value
    if (description?.isNotBlank() == true) {
        descriptionArea.value = formatBody(description)
    } else {
        TODO("alert failed to format description. area=$descriptionArea / content=$description")
    }
}

private fun findCommitMessageBodyTextArea(): HTMLTextAreaElement? {
    val element = document.run { gitHubDescriptionArea ?: gitLabDescriptionArea }
    return element as? HTMLTextAreaElement
}

private val Document.gitHubDescriptionArea get() = querySelector("#pull_request_body")
private val Document.gitLabDescriptionArea get() = querySelector("#merge_request_description")
