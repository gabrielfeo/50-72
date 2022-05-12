package browser.dom

expect class Document {
    val activeElement: Element?
    fun querySelector(selectors: String): Element?

    fun execCommand(
        commandId: String,
        showUI: Boolean,
        value: String,
    ): Boolean
}
