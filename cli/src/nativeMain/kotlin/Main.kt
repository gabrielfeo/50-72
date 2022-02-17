import platform.posix.exit
import platform.posix.fprintf
import platform.posix.stderr

fun main(args: Array<String>) {
    try {
        require(args.size == 1) { "Expected args: <commit-message>" }
        val message = args.first()
        print(formatFullMessage(message))
    } catch (error: Throwable) {
        printError(error.message)
        exit(1)
    }
}

fun printError(message: String?) {
    if (message.isNullOrBlank())
        return
    fprintf(stderr, "$message\n")
}
