package cli.command.hook.install

import cli.commons.*
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.output.TermUi.echo
import okio.FileSystem

fun interface InstallAction {
    operator fun invoke()
}

const val FAILED_TO_SET_PERMISSIONS_MSG = """
Created the hook file, but failed to set execute permissions on it.
Please set permissions manually by running 'chmod' so that Git can run the hook:
    chmod 755 $PREPARE_COMMIT_MSG_PATH
"""

class InstallActionImpl(
    private val fileSystem: FileSystem = defaultFileSystem,
    private val permissionSetter: FilePermissionSetter = createFilePermissionSetter(),
) : InstallAction {

    override fun invoke() {
        prepareCommitMsg.run {
            if (exists(fileSystem)) {
                checkNotInstalled()
                appendText("\n$FORMAT_FILE_COMMAND\n", fileSystem)
            } else {
                writeText("$SHEBANG\n\n$FORMAT_FILE_COMMAND\n", fileSystem)
                setHookFilePermissions()
            }
        }
        echo("Done! Please ensure 50-72 is in your PATH.")
    }

    private fun checkNotInstalled() {
        val installed = prepareCommitMsg.readLines(fileSystem).any { it.startsWith("50-72") }
        if (installed) {
            throw PrintMessage("Already installed.")
        }
    }

    private fun setHookFilePermissions() {
        val usualHookPermissions = PermissionSet.`755`
        try {
            permissionSetter.set(PREPARE_COMMIT_MSG_PATH, usualHookPermissions)
        } catch (error: IllegalStateException) {
            throw PrintMessage(FAILED_TO_SET_PERMISSIONS_MSG, error = true)
        }
    }
}