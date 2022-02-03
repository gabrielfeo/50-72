package browser.scripting

class ScriptInjection(
    val target: InjectionTarget,
    val func: (() -> Unit)? = null,
    val files: Array<String>? = null,
) {
    class InjectionTarget(val tabId: String)
}