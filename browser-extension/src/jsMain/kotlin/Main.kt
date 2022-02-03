import ScriptInjection.InjectionTarget
import kotlinx.browser.document

external val chrome: dynamic
class ContextMenu(
    val id: String,
    val title: String,
    val contexts: Array<String>,
)

class Tab(val id: String)

class ScriptInjection(
    val target: InjectionTarget,
    val func: () -> Unit
) {
    class InjectionTarget(val tabId: String)
}

class OnClickData(
    val menuItemId: String,
    val editable: Boolean,
    val selectionText: String?,
)

private const val MENU_ITEM_ID = "foo"

fun main() {
    chrome.runtime.onInstalled.addListener {
        chrome.contextMenus.create(
            ContextMenu(
                id = MENU_ITEM_ID,
                title = MENU_ITEM_ID,
                contexts = arrayOf(
                    "page",
                    "editable",
                ),
            ),
            {
                console.log("Menu created!")
            }
        )
        chrome.contextMenus.onClicked.addListener { data: OnClickData, tab: Tab ->
            injectFunction(tab) {
                val descriptionTextArea = document.querySelector("#merge_request_description")
                descriptionTextArea?.let {
                    val description = it.textContent
                    if (description?.isNotBlank() == true) {
                        it.textContent = format(description)
                    } else {
                        TODO("alert")
                    }
                }
            }
        }
        console.log("Hello world!")
    }
}

private fun injectFunction(tab: Tab, func: () -> Unit) {
    val script = ScriptInjection(
        target = InjectionTarget(tabId = tab.id),
        func,
    )
    console.log("Will inject to tab ${tab.id}")
    chrome.scripting.executeScript(script)
}
