package browser.dom

import kotlinx.dom.appendElement as kotlinAppend

actual typealias Element = org.w3c.dom.Element

actual fun Element.addEventListener(type: String, listener: () -> Unit) {
    addEventListener(type, { listener() })
}

actual fun Element.appendElement(name: String, configure: Element.() -> Unit) {
    kotlinAppend(name, configure)
}

actual typealias HTMLElement = org.w3c.dom.HTMLElement

actual typealias HTMLTextAreaElement = org.w3c.dom.HTMLTextAreaElement
