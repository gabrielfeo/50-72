import org.w3c.dom.*

fun interface MessageBodyFinder {
    fun find(root: Element): HTMLTextAreaElement?
}

object RealMessageBodyFinder : MessageBodyFinder {

    private val ParentNode.gitLabBodyArea
        get() = querySelector("#merge_request_description")

    private val ParentNode.gitHubBodyArea
        get() = querySelector("#pull_request_body")
            ?: querySelector("[name='pull_request[body]']")

    override fun find(root: Element): HTMLTextAreaElement? {
        return with(root) {
            (gitHubBodyArea ?: gitLabBodyArea) as? HTMLTextAreaElement
        }
    }
}
