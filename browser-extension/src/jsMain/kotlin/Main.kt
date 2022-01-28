external val chrome: dynamic
class ContextMenu(
    val id: String,
    val title: String,
    val contexts: Array<String>,
)

fun main() {
    chrome.runtime.onInstalled.addListener {
        chrome.contextMenus.create(
            ContextMenu(
                id = "foo",
                title = "foo",
                contexts = arrayOf(
                    "page",
                    "selection",
                    "link",
                    "editable",
                    "image",
                    "video",
                    "audio"
                ),
            ),
            { a, b ->
                console.log("callback: ${a?.toString()} / ${b?.toString()}")
            }
        )
        chrome.contextMenus.onClicked.addListener { a, b ->
            console.log(a)
            console.log(b)
        }
        console.log("Hello world!")
    }
}

const val COLOR = "#3aa757";
