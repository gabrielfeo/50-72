import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import okio.FileSystem
import okio.Path.Companion.toPath
import platform.posix.*

private const val PREPARE_COMMIT_MSG_PATH = ".git/hooks/prepare-commit-msg"
private const val SHEBANG = "#!/usr/bin/env sh"
private const val FORMAT_FILE_COMMAND = "50-72 format-file \"$1\""

class InstallHook(
    private val fileSystem: FileSystem = FileSystem.SYSTEM,
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
                appendText("\n\n$FORMAT_FILE_COMMAND\n", fileSystem)
            } else {
                writeText("$SHEBANG\n\n$FORMAT_FILE_COMMAND\n", fileSystem)
                setHookFilePermissions()
            }
        }
    }

    private fun uninstall() {
        prepareCommitMsg.run {
            if (!exists(fileSystem)) {
                return
            }

            val hookLines = readText(fileSystem).lines()
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