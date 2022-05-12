package browser.dom

import kotlinx.dom.appendElement as kotlinAppend

actual typealias Event = org.w3c.dom.events.Event

actual typealias EventTarget = org.w3c.dom.events.EventTarget

actual typealias Node = org.w3c.dom.Node

actual typealias ParentNode = org.w3c.dom.ParentNode

actual typealias Element = org.w3c.dom.Element

actual fun Element.addEventListener(type: String, listener: () -> Unit) {
    addEventListener(type, { listener() })
}

actual fun Element.appendElement(name: String, configure: Element.() -> Unit) {
    kotlinAppend(name, configure)
}

actual typealias HTMLElement = org.w3c.dom.HTMLElement

actual typealias HTMLButtonElement = org.w3c.dom.HTMLButtonElement

actual typealias HTMLTextAreaElement = org.w3c.dom.HTMLTextAreaElement

actual typealias Document = org.w3c.dom.Document
