import kotlinx.browser.document
import org.w3c.dom.Document
import org.w3c.dom.HTMLTextAreaElement

fun main() {
    val descriptionArea = findCommitMessageBodyTextArea()
    val description = descriptionArea?.textContent
    if (description?.isNotBlank() == true) {
        TODO("Change formatter to support passing body only")
        descriptionArea.textContent = format(description)
    } else {
        TODO("alert failed to format description. area=$descriptionArea / content=$description")
    }
}

private fun findCommitMessageBodyTextArea(): HTMLTextAreaElement? {
    val element = document.run { gitHubDescriptionArea ?: gitLabDescriptionArea }
    return element as? HTMLTextAreaElement
}

private val Document.gitLabDescriptionArea get() = querySelector("[name='issue[body]']")
private val Document.gitHubDescriptionArea get() = querySelector("#merge_request_description")
