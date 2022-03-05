import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import platform.posix.exit
import platform.posix.fprintf
import platform.posix.stderr

val parser = ArgParser("50-72")

val message by parser
    .argument(ArgType.String, "message", description = "Commit message to be formatted")

val isMarkdown by parser
    .option(ArgType.Boolean, "markdown", description = "Set when message is in Markdown format")
    .default(false)

fun main(args: Array<String>) {
    try {
        parser.parse(args)
        val message = formatFullMessage(message, isMarkdown)
        print(message)
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
