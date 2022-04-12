import kotlinx.dom.appendElement
import org.w3c.dom.Element

fun main2(parent: Element) {
    parent.appendElement("button") {
        innerHTML = "Format"
    }
}
