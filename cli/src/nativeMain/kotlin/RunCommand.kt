import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import okio.FileSystem
import okio.Path
import okio.buffer
import okio.use

private const val DEFAULT_GIT_MSG_FILE = ".git/EDIT_COMMITMSG"

class Main(
    private val format: (message: String, isMarkdown: Boolean) -> String = ::formatFullMessage,
) : CliktCommand() {

    private val message by argument(help = "Commit message to be formatted")
        .default("")

    private val isMarkdown by option("--markdown", help = "Set when message is in Markdown format")
        .flag(default = false)

    private val messageFile by option("--message-file", help = "Path to a commit message file", hidden = true)

    override fun run() {
        when (messageFile) {
            null -> formatMessageArgument()
            else -> formatFileAsHook()
        }
    }

    private fun formatMessageArgument() {
        try {
            val formattedMessage = format(message, isMarkdown)
            echo(formattedMessage)
        } catch (error: IllegalArgumentException) {
            throw UsageError(error.message.orEmpty(), paramName = "message")
        }
    }

    private fun formatFileAsHook() {
        TODO()
    }

}

fun Path.readToString(): String {
    FileSystem.SYSTEM.source(this).use { fileSource ->
        fileSource.buffer().use { bufferedFileSource ->
            return buildString {
                while (true) {
                    val line = bufferedFileSource.readUtf8Line() ?: break
                    appendLine(line)
                }
            }
        }
    }
}
