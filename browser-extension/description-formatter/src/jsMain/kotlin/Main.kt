import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.Document
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLTextAreaElement

const val SUPPORT_ADDRESS = "gabriel@gabrielfeo.com"

fun main() {
    val bodyArea = findCommitMessageBodyTextArea().also { log.debug("Found element $it") }
    val description = bodyArea?.value
    log.debug("desc $description")
    if (description == null || description.isBlank() || !replaceBody(bodyArea)) {
        alertFailedToFindBodyArea()
        logFailedToFindBodyArea(bodyArea)
    } else {
        log.debug("Ran insert")
    }
}

private fun findCommitMessageBodyTextArea(): HTMLTextAreaElement? {
    val element = document.run { gitHubBodyArea ?: gitLabBodyArea }
    return element as? HTMLTextAreaElement
}

private val Document.gitLabBodyArea
    get() = querySelector("#merge_request_description")

private val Document.gitHubBodyArea
    get() = querySelector("#pull_request_body")
        ?: querySelector("[name='pull_request[body]']")

private fun alertFailedToFindBodyArea() {
    window.alert("""
        Couldn't find an PR/MR description area in this page :(
        
        Please report this to $SUPPORT_ADDRESS with:
          • the current page URL (please remove any sensitive data)
          • a screenshot of the page's Console (only the log entry starting with [50-72])
    """.trimIndent())
}

private fun logFailedToFindBodyArea(bodyArea: HTMLTextAreaElement?) {
    log.error(
        """
            [50-72] Failed to format description.
              bodyArea=$bodyArea
                bodyArea.textContent=${bodyArea?.textContent} 
                bodyArea.value=${bodyArea?.value} 
        """.trimIndent()
    )
}

private fun replaceBody(bodyArea: HTMLTextAreaElement): Boolean {
    val previouslyFocused = document.activeElement as? HTMLElement
    log.debug("Previous el $previouslyFocused")
    try {
        val formattedBody = formatBody(bodyArea.value)
        bodyArea.run { focus(); select() }
        return document.execCommand("insertText", showUI = false, value = formattedBody)
    } finally {
        previouslyFocused?.focus()
    }
}
