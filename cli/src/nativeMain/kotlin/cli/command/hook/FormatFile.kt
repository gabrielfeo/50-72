package cli.command.hook

import cli.commons.readText
import cli.commons.writeText
import cli.defaultFileSystem
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import formatFullMessage
import okio.FileSystem
import okio.Path.Companion.toPath

private const val DEFAULT_GIT_MSG_FILE = ".git/EDIT_COMMITMSG"

class FormatFile(
    private val fileSystem: FileSystem = defaultFileSystem,
    private val format: (message: String, isMarkdown: Boolean) -> String = ::formatFullMessage,
) : CliktCommand(
    name = "format-file",
    help = "Format the git commit message file (or another file)".trimIndent()
) {

    private val messageFile by argument(
        "file",
        help = "Path to the message file. Default is the standard git file, $DEFAULT_GIT_MSG_FILE"
    ).default(DEFAULT_GIT_MSG_FILE)

    override fun run() {
        val file = messageFile.toPath()
        val content = file.readText(fileSystem)
        val formattedContent = format(content, false)
        file.writeText(formattedContent, fileSystem)
    }
}