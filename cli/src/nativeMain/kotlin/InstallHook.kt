import com.github.ajalt.clikt.core.CliktCommand
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.use

private const val PREPARE_COMMIT_MSG_PATH = ".git/hooks/prepare-commit-msg"
private const val FORMAT_FILE_COMMAND = "50-72 format-file $1"

class InstallHook : CliktCommand(name = "install-hook") {

    private val prepareCommitMsg by lazy { PREPARE_COMMIT_MSG_PATH.toPath() }

    override fun run() {
        prepareCommitMsg.run {
            if (exists()) {
                appendText("\n\n$FORMAT_FILE_COMMAND\n")
            } else {
                writeText("$FORMAT_FILE_COMMAND\n")
            }
        }
    }

    private fun Path.exists(): Boolean = FileSystem.SYSTEM.exists(this)

    private fun Path.appendText(text: String) {
        FileSystem.SYSTEM.appendingSink(this).buffer().use {
            it.writeUtf8(text)
        }
    }

    private fun Path.writeText(text: String) {
        FileSystem.SYSTEM.write(this, mustCreate = true) {
            writeUtf8(text)
        }
    }

}