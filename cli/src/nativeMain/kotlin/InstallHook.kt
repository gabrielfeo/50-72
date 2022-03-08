import com.github.ajalt.clikt.core.CliktCommand
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.use

private const val PREPARE_COMMIT_MSG_PATH = ".git/hooks/prepare-commit-msg"
private const val PREPARE_COMMIT_MSG_SCRIPT = "\n\n50-72 --message-file $1\n"

class InstallHook : CliktCommand() {

    override fun run() {
        installHook()
    }

    private fun installHook() {
        val hookFile = PREPARE_COMMIT_MSG_PATH.toPath()
        hookFile.appendText(PREPARE_COMMIT_MSG_SCRIPT)
    }

    private fun Path.appendText(text: String) {
        FileSystem.SYSTEM.appendingSink(this).buffer().use {
            it.writeUtf8(text)
        }
    }
}