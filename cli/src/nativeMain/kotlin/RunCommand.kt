import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.use

private const val DEFAULT_GIT_MSG_FILE = ".git/EDIT_COMMITMSG"
private const val PREPARE_COMMIT_MSG_PATH = ".git/hooks/prepare-commit-msg"
private const val PREPARE_COMMIT_MSG_SCRIPT = "\n\n50-72 --message-file $1\n"

// TODO Try out subcommands with invokeWithoutSubcommands=true
// TODO --uninstall-hook: remove line with '50-72' from hook
// TODO Add internal option for executable path
// TODO Try replacing messageFile for sh logic in hook
// TODO chmod hook file
// TODO Add shebang if new file?

class Main(
    private val format: (message: String, isMarkdown: Boolean) -> String = ::formatFullMessage,
) : CliktCommand() {

    private val message by argument(help = "Commit message to be formatted")
        .default("")

    private val isMarkdown by option("--markdown", help = "Set when message is in Markdown format")
        .flag(default = false)

    private val shouldInstallHook by option(
        "--install-hook",
        help = "Install git hook to format messages automatically in the current repository",
    ).flag(default = false)

    private val messageFile by option("--message-file", hidden = true)

    override fun run() {
        if (shouldInstallHook) {
            installHook()
            return
        }
        when (messageFile) {
            null -> formatMessageArgument()
            else -> formatFileAsHook()
        }
    }

    private fun installHook() {
        val hookFile = PREPARE_COMMIT_MSG_PATH.toPath()
        hookFile.appendText(PREPARE_COMMIT_MSG_SCRIPT)
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
        val file = (messageFile ?: DEFAULT_GIT_MSG_FILE).toPath()
        val content = file.readText()
        val formattedContent = format(content, false)
        file.writeText(formattedContent)
    }

    private fun Path.readText(): String {
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

    private fun Path.writeText(text: String) {
        FileSystem.SYSTEM.write(this) {
            writeUtf8(text)
        }
    }

    private fun Path.appendText(text: String) {
        FileSystem.SYSTEM.appendingSink(this).buffer().use {
            it.writeUtf8(text)
        }
    }

}
