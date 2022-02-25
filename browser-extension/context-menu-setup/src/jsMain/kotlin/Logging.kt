private var debugLogging = true

val log = object : Log {}

interface Log {
    fun error(message: String) = log("ERROR", message)
    fun info(message: String) = log("INFO", message)
    fun debug(message: String) {
        if (debugLogging) {
            log("DEBUG", message)
        }
    }
}

private fun log(level: String, message: String) {
    console.log("[50-72] $level: $message")
}
