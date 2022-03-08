import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import platform.posix.fprintf
import platform.posix.stderr
import platform.posix.exit as posixExit

inline fun runCommand(
    args: Array<String>,
    format: (message: String, isMarkdown: Boolean) -> String = ::formatFullMessage,
    printStdout: (message: String) -> Unit = ::print,
    printStderr: (message: String) -> Unit = ::printError,
    exit: (code: Int) -> Unit = ::posixExit,
) {
    val parser = ArgParser("50-72")

    val message by parser
        .argument(ArgType.String, "message", description = "Commit message to be formatted")

    val isMarkdown by parser
        .option(ArgType.Boolean, "markdown", description = "Set when message is in Markdown format")
        .default(false)

    try {
        parser.parse(args)
        val formattedMessage = format(message, isMarkdown)
        printStdout(formattedMessage)
    } catch (error: Throwable) {
        printStderr(error.message.orEmpty())
        exit(1)
    }
}

inline fun printError(message: String?) {
    if (message.isNullOrBlank())
        return
    fprintf(stderr, "$message\n")
}
