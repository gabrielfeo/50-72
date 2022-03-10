import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.use
import platform.posix.*

private const val PREPARE_COMMIT_MSG_PATH = ".git/hooks/prepare-commit-msg"
private const val SHEBANG = "#!/usr/bin/env sh"
private const val FORMAT_FILE_COMMAND = "50-72 format-file $1"

class InstallHook(
    private val fileSystem: FileSystem = FileSystem.SYSTEM,
) : CliktCommand(name = "install-hook") {

    private val prepareCommitMsg by lazy { PREPARE_COMMIT_MSG_PATH.toPath() }

    override fun run() {
        prepareCommitMsg.run {
            if (exists()) {
                appendText("\n\n$FORMAT_FILE_COMMAND\n")
            } else {
                writeText("$SHEBANG\n\n$FORMAT_FILE_COMMAND\n")
                setHookFilePermissions()
            }
        }
    }

    private fun Path.exists(): Boolean = fileSystem.exists(this)

    private fun Path.appendText(text: String) {
        fileSystem.appendingSink(this).buffer().use {
            it.writeUtf8(text)
        }
    }

    private fun Path.writeText(text: String) {
        fileSystem.write(this, mustCreate = true) {
            writeUtf8(text)
        }
    }

    private fun setHookFilePermissions() {
        val usualHookPermissions = S_IRWXU.or(S_IRGRP).or(S_IXGRP).or(S_IXOTH).toUShort()
        val result = chmod(PREPARE_COMMIT_MSG_PATH, usualHookPermissions)
        if (result != 0) {
            throw PrintMessage(chmodFailedMessage(errno), error = true)
        }
    }

    private fun chmodFailedMessage(errno: Int) = """
        Created the hook file, but failed to set execute permissions on it (errno=$errno).
        Please set permissions manually by running 'chmod' so that Git can run the hook:
            chmod 755 $PREPARE_COMMIT_MSG_PATH
    """.trimIndent()

}