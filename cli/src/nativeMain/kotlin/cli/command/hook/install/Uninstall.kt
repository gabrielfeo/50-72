package cli.command.hook.install

import cli.commons.delete
import cli.commons.exists
import cli.commons.readLines
import cli.commons.writeText
import com.github.ajalt.clikt.output.TermUi.echo
import okio.FileSystem

fun interface UninstallAction {
    operator fun invoke()
}

class UninstallActionImpl(
    private val fileSystem: FileSystem = FileSystem.SYSTEM,
) : UninstallAction {

    override fun invoke() {
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
}