import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import okio.FileSystem
import okio.Path.Companion.toPath

internal const val PREPARE_COMMIT_MSG_PATH = ".git/hooks/prepare-commit-msg"
internal const val SHEBANG = "#!/usr/bin/env sh"
internal const val FORMAT_FILE_COMMAND = "50-72 format-file \"$1\""

internal const val FAILED_TO_SET_PERMISSIONS_MSG = """
Created the hook file, but failed to set execute permissions on it.
Please set permissions manually by running 'chmod' so that Git can run the hook:
    chmod 755 $PREPARE_COMMIT_MSG_PATH
"""

class Hook(
    private val fileSystem: FileSystem = FileSystem.SYSTEM,
    private val permissionSetter: FilePermissionSetter = Chmod(),
) : CliktCommand(
    name = "hook",
    help = """
        Install the 50-72 git hook in the current repository.
        
        This is basically adding '$FORMAT_FILE_COMMAND' to the 'prepare-commit-msg' hook. If the
        hook file exists, it will be appended to, else a new one will be created.
    """.trimIndent()
) {

    private val prepareCommitMsg by lazy { PREPARE_COMMIT_MSG_PATH.toPath() }

    private val install: Boolean by option().flag().validate { require(it || uninstall) }
    private val uninstall: Boolean by option().flag().validate { require(it || install) }

    override fun run() {
        checkGitDirExists()
        when {
            install -> install()
            uninstall -> uninstall()
            else -> throw UsageError("No action specified")
        }
    }

    private fun install() {
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

    private fun uninstall() {
        prepareCommitMsg.run {
            if (!exists(fileSystem)) {
                echo("Not installed.")
                return
            }

            val hookLines = readLines(fileSystem).toList()
            val containsOurCommandOnly = hookLines.all {
                it.startsWith("#!") || it.startsWith("50-72") || it.isBlank()
            }
            if (containsOurCommandOnly) {
                delete(fileSystem)
            } else {
                val hookWithoutOurCommand = hookLines
                    .filterNot { it.startsWith("50-72") }
                    .joinToString(separator = "\n")
                writeText(hookWithoutOurCommand, fileSystem)
            }
        }
        echo("Successfully uninstalled!")
    }

    private fun checkGitDirExists() {
        if(!".git".toPath().exists(fileSystem)) {
            throw UsageError("Current directory is not a git repository")
        }
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