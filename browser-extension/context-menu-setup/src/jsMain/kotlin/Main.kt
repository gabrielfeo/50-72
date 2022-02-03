import ScriptInjection.InjectionTarget

external val browser: dynamic
class ContextMenu(
    val id: String,
    val title: String,
    val contexts: Array<String>,
)

class Tab(val id: String)

class ScriptInjection(
    val target: InjectionTarget,
    val func: (() -> Unit)? = null,
    val files: Array<String>? = null,
) {
    class InjectionTarget(val tabId: String)
}

class OnClickData(
    val menuItemId: String,
    val editable: Boolean,
    val selectionText: String?,
)

private const val MENU_ITEM_ID = "foo"

fun onMenuCreated() {
    console.log("Menu created!")
}

private const val FORMATTER_SCRIPT_FILENAME = "description-formatter.js"

fun onMenuClicked(data: OnClickData, tab: Tab) {
    check(data.menuItemId == MENU_ITEM_ID)
    console.log("Menu clicked!")
    injectFile(tab, FORMATTER_SCRIPT_FILENAME)
}

val menu = ContextMenu(
    id = MENU_ITEM_ID,
    title = MENU_ITEM_ID,
    contexts = arrayOf(
        "page",
        "editable",
    ),
)

fun main() {
    browser.runtime.onInstalled.addListener {
        browser.contextMenus.create(menu, ::onMenuCreated)
        browser.contextMenus.onClicked.addListener(::onMenuClicked)
    }
}

private fun injectFile(tab: Tab, file: String) {
    val script = ScriptInjection(
        target = InjectionTarget(tabId = tab.id),
        files = arrayOf(file),
    )
    console.log("Will inject to tab ${tab.id}")
    browser.scripting.executeScript(script)
}
