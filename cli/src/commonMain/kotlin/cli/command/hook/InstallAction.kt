/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.command.hook

import cli.commons.*
import cli.env.Environment
import cli.env.RealEnvironment
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.output.TermUi
import okio.FileSystem
import okio.Path

fun interface InstallAction {
    operator fun invoke(markdownFormat: Boolean)
}

const val FAILED_TO_SET_PERMISSIONS_MSG = """
Created the hook file, but failed to set execute permissions on it.
Please set permissions manually by running 'chmod' so that Git can run the hook:
    chmod 755 $PREPARE_COMMIT_MSG_PATH
"""

const val INSTALL_DONE_MSG = "Done! Please ensure 50-72 is in your PATH."

const val MARKDOWN_HEADING_CHAR = '#'
const val MARKDOWN_COMMENT_CHAR_ADVICE = """
You must set 'git config core.commentChar' to something other than default '#',
otherwise git will ignore Markdown headers, which also start with '#'.
    git config core.commentChar ';'
"""

internal const val ALREADY_INSTALLED_MSG = "Already installed."

class InstallActionImpl(
    private val fileSystem: FileSystem = defaultFileSystem,
    private val permissionSetter: FilePermissionSetter = createFilePermissionSetter(),
    private val env: Environment = RealEnvironment(defaultCommandRunner),
    private val echo: (msg: String) -> Unit,
) : InstallAction {

    override fun invoke(markdownFormat: Boolean) {
        val command = commandForOption(markdownFormat)
        install(command)
        echo(INSTALL_DONE_MSG)
        if (markdownFormat && env.gitCommentChar() == MARKDOWN_HEADING_CHAR) {
            echo(MARKDOWN_COMMENT_CHAR_ADVICE)
        }
    }

    private fun install(command: String) {
        prepareCommitMsg.run {
            if (exists(fileSystem)) {
                checkNotInstalledInHook()
                appendCommand(command)
            } else {
                createWithCommand(command)
            }
        }
    }

    private fun commandForOption(markdownFormat: Boolean) = when {
        markdownFormat -> FORMAT_FILE_AS_MARKDOWN_COMMAND
        else -> FORMAT_FILE_AS_PLAIN_TEXT_COMMAND
    }

    private fun Path.appendCommand(command: String) {
        appendText("\n$command\n", fileSystem)
    }

    private fun Path.createWithCommand(command: String) {
        writeText("$SHEBANG\n\n$command\n", fileSystem)
        setHookFilePermissions()
    }

    private fun checkNotInstalledInHook() {
        val installed = prepareCommitMsg.readLines(fileSystem).any { it.startsWith("50-72") }
        if (installed) {
            throw PrintMessage(ALREADY_INSTALLED_MSG)
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