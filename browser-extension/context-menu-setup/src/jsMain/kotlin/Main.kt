import browser.browser
import browser.contextmenus.ContextMenu
import browser.contextmenus.OnClickData
import browser.contextmenus.Tab
import browser.scripting.ScriptInjection
import browser.scripting.ScriptInjection.InjectionTarget

private const val MENU_ITEM_ID = "50-72-format-menu"
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
        browser.contextMenus.create(menu)
        browser.contextMenus.onClicked.addListener(::onMenuClicked)
        log.debug("Context menu created")
    }
}


private fun onMenuClicked(data: OnClickData, tab: Tab) {
    log.debug("Clicked in tab ${tab.id}")
    check(data.menuItemId == MENU_ITEM_ID)
    injectFile(tab, FORMATTER_SCRIPT_FILENAME)
}

private fun injectFile(tab: Tab, file: String) {
    val script = ScriptInjection(
        target = InjectionTarget(tabId = tab.id),
        files = arrayOf(file),
    )
    browser.scripting.executeScript(script)
    log.debug("Injected script in tab ${tab.id}")
}
