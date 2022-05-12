import kotlinx.browser.document
import org.w3c.dom.*

fun interface MessageBodyFormatter {
    fun format(
        bodyElement: HTMLTextAreaElement,
        format: (String, Char, Boolean) -> String,
    )
}

object RealMessageBodyFormatter : MessageBodyFormatter {

    override fun format(
        bodyElement: HTMLTextAreaElement,
        format: (String, Char, Boolean) -> String,
    ) {
        val description = bodyElement.value
        if (description.isNotBlank()) {
            check(replaceBody(bodyElement, format))
        }
    }

    private fun replaceBody(
        bodyArea: HTMLTextAreaElement,
        format: (String, Char, Boolean) -> String,
    ): Boolean {
        val previouslyFocused = document.activeElement as? HTMLElement
        try {
            val formattedBody = format(bodyArea.value, '#', true)
            bodyArea.run { focus(); select() }
            return document.execCommand("insertText", showUI = false, value = formattedBody)
        } finally {
            previouslyFocused?.focus()
        }
    }
}
