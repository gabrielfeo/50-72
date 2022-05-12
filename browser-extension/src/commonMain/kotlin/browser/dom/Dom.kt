package browser.dom

expect open class Event constructor() {
    val type: String
}

expect abstract class EventTarget {
    fun dispatchEvent(event: Event): Boolean
}

expect abstract class Node : EventTarget

expect interface ParentNode {
    fun querySelector(selectors: String): Element?
}

expect abstract class Element : Node, ParentNode {
    var id: String
    var innerHTML: String
    fun setAttribute(qualifiedName: String, value: String)
    abstract fun remove()
}

expect fun Element.addEventListener(type: String, listener: () -> Unit)
expect fun Element.appendElement(name: String, configure: Element.() -> Unit)

expect abstract class HTMLElement : Element {
    fun focus()
}

expect abstract class HTMLButtonElement : HTMLElement

expect abstract class HTMLTextAreaElement : HTMLElement {
    var value: String
    fun select()
}

expect class Document : ParentNode {
    var body: HTMLElement?
    val activeElement: Element?
    fun getElementById(elementId: String): Element?
    override fun querySelector(selectors: String): Element?

    fun execCommand(
        commandId: String,
        showUI: Boolean,
        value: String,
    ): Boolean
}
