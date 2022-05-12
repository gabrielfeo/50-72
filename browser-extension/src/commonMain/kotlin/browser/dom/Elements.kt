package browser.dom

expect abstract class Element {
    var innerHTML: String
    fun setAttribute(qualifiedName: String, value: String)
}

expect fun Element.addEventListener(type: String, listener: () -> Unit)
expect fun Element.appendElement(name: String, configure: Element.() -> Unit)

expect abstract class HTMLElement : Element {
    fun focus()
}

expect abstract class HTMLTextAreaElement : HTMLElement {
    var value: String
    fun select()
}
