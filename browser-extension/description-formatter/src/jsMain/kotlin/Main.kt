import kotlinx.browser.document

fun main() {
    val descriptionTextArea = document.querySelector("#merge_request_description")
    descriptionTextArea?.let {
        val description = it.textContent
        if (description?.isNotBlank() == true) {
            TODO("Change formatter to support passing body only")
            it.textContent = format(description)
        } else {
            TODO("alert")
        }
    }
}