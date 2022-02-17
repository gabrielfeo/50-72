import browser.browser
import browser.contextmenus.ContextMenu
import browser.contextmenus.OnClickData
import browser.contextmenus.Tab
import browser.scripting.ScriptInjection
import browser.scripting.ScriptInjection.InjectionTarget

private const val MENU_ITEM_ID = "foo"
private const val MENU_ITEM_TITLE = "Format PR/MR description"
private const val FORMATTER_SCRIPT_FILENAME = "description-formatter.js"

private val menu = ContextMenu(
    id = MENU_ITEM_ID,
    title = MENU_ITEM_TITLE,
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

private fun onMenuCreated() {
    console.log("Menu created!")
}


private fun onMenuClicked(data: OnClickData, tab: Tab) {
    check(data.menuItemId == MENU_ITEM_ID)
    console.log("Menu clicked!")
    injectFile(tab, FORMATTER_SCRIPT_FILENAME)
}

private fun injectFile(tab: Tab, file: String) {
    val script = ScriptInjection(
        target = InjectionTarget(tabId = tab.id),
        files = arrayOf(file),
    )
    console.log("Will inject to tab ${tab.id}")
    browser.scripting.executeScript(script)
}
