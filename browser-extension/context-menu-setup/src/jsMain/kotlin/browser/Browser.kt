package browser

@JsName("internalBrowser")
val browser: dynamic = when {
    jsTypeOf(js("browser")) != "undefined" -> js("browser")
    jsTypeOf(js("chrome")) != "undefined" -> js("chrome")
    else -> error("Unsupported browser")
}
