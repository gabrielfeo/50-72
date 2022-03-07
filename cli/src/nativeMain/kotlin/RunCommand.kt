import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default

inline fun runCommand(
    args: Array<String>,
    format: (message: String, isMarkdown: Boolean) -> String,
    printStdout: (message: String) -> Unit,
    printStderr: (message: String) -> Unit,
    exit: (code: Int) -> Unit,
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
