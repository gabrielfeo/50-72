import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.UsageError
import okio.FileSystem
import okio.Path.Companion.toPath
import platform.posix.*

private const val PREPARE_COMMIT_MSG_PATH = ".git/hooks/prepare-commit-msg"
private const val SHEBANG = "#!/usr/bin/env sh"
private const val FORMAT_FILE_COMMAND = "50-72 format-file $1"

class InstallHook(
    private val fileSystem: FileSystem = FileSystem.SYSTEM,
) : CliktCommand(name = "install-hook") {

    private val prepareCommitMsg by lazy { PREPARE_COMMIT_MSG_PATH.toPath() }

    override fun run() {
        checkGitDirExists()
        prepareCommitMsg.run {
            if (exists(fileSystem)) {
                appendText("\n\n$FORMAT_FILE_COMMAND\n", fileSystem)
            } else {
                writeText("$SHEBANG\n\n$FORMAT_FILE_COMMAND\n", fileSystem)
                setHookFilePermissions()
            }
        }
    }

    private fun checkGitDirExists() {
        if(!".git".toPath().exists(fileSystem)) {
            throw UsageError("Current directory is not a git repository")
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