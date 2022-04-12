import kotlinx.dom.appendElement
import org.w3c.dom.Element

fun main2(
    parent: Element,
    format: (String, Boolean) -> String = ::formatBody,
) {
    parent.appendElement("button") {
        innerHTML = "Format"
    }
    format("", true)
}


