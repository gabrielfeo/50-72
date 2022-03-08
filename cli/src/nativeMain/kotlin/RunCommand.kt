import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import platform.posix.fprintf
import platform.posix.stderr
import platform.posix.exit as posixExit

class Main(
    private val format: (message: String, isMarkdown: Boolean) -> String = ::formatFullMessage,
    private val printStdout: (message: String) -> Unit = ::print,
    private val printStderr: (message: String) -> Unit = ::printError,
    private val exit: (code: Int) -> Unit = ::posixExit,
) : CliktCommand() {

    private val message by argument("message", help = "Commit message to be formatted")

    private val isMarkdown by option("--markdown", help = "Set when message is in Markdown format")

    override fun run() {
        try {
            val formattedMessage = format(message, isMarkdown.toBoolean())
            printStdout(formattedMessage)
        } catch (error: Throwable) {
            printStderr(error.message.orEmpty())
            exit(1)
        }
    }

}

inline fun printError(message: String?) {
    if (message.isNullOrBlank())
        return
    fprintf(stderr, "$message\n")
}
