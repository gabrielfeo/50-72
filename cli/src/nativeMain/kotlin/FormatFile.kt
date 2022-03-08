import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.use

private const val DEFAULT_GIT_MSG_FILE = ".git/EDIT_COMMITMSG"

class FormatFile(
    private val format: (message: String, isMarkdown: Boolean) -> String = ::formatFullMessage,
) : CliktCommand() {

    private val messageFile by argument("file").default(DEFAULT_GIT_MSG_FILE)

    override fun run() {
        val file = messageFile.toPath()
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
}