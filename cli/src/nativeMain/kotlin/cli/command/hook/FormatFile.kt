package cli.command.hook

import cli.commons.readText
import cli.commons.writeText
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import formatFullMessage
import okio.FileSystem
import okio.Path.Companion.toPath

private const val DEFAULT_GIT_MSG_FILE = ".git/EDIT_COMMITMSG"

class FormatFile(
    private val fileSystem: FileSystem = FileSystem.SYSTEM,
    private val format: (message: String, isMarkdown: Boolean) -> String = ::formatFullMessage,
) : CliktCommand() {

    private val messageFile by argument("file").default(DEFAULT_GIT_MSG_FILE)

    override fun run() {
        val file = messageFile.toPath()
        val content = file.readText(fileSystem)
        val formattedContent = format(content, false)
        file.writeText(formattedContent, fileSystem)
    }
}