/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

package cli.command.hook.install

import cli.commons.defaultFileSystem
import cli.commons.exists
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import okio.FileSystem
import okio.Path.Companion.toPath

const val PREPARE_COMMIT_MSG_PATH = ".git/hooks/commit-msg"
val prepareCommitMsg = PREPARE_COMMIT_MSG_PATH.toPath()

const val SHEBANG = "#!/usr/bin/env sh"
const val FORMAT_FILE_AS_PLAIN_TEXT_COMMAND = "50-72 format-file \"$1\""
const val FORMAT_FILE_AS_MARKDOWN_COMMAND = "50-72 format-file --markdown \"$1\""

const val NOT_A_GIT_DIR_MSG = "Current directory is not a git repository"

class Hook(
    private val fileSystem: FileSystem = defaultFileSystem,
    private val installAction: InstallAction = InstallActionImpl(),
    private val uninstallAction: UninstallAction = UninstallActionImpl(),
) : CliktCommand(
    name = "hook",
    help = """
        Install the 50-72 git hook in the current repository.
        
        This is basically adding '$FORMAT_FILE_AS_PLAIN_TEXT_COMMAND' to the 'commit-msg' hook. If the
        hook file exists, it will be appended to, else a new one will be created.
    """.trimIndent()
) {

    private val install: Boolean by option(
        help = "Install the hook"
    ).flag().validate { require(it || uninstall) }

    private val uninstall: Boolean by option(
        help = "Uninstall the hook"
    ).flag().validate { require(it || install) }

    private val markdownFormat by option(
        "--markdown",
        help = "Set if commit messages are written in the Markdown format. Default: false."
    ).flag()

    override fun run() {
        checkGitDirExists()
        when {
            install -> installAction(markdownFormat)
            uninstall -> uninstallAction()
            else -> error("Can't happen thanks to validate calls")
        }
    }

    private fun checkGitDirExists() {
        if(!".git".toPath().exists(fileSystem)) {
            throw UsageError(NOT_A_GIT_DIR_MSG)
        }
    }
}