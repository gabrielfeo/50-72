/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

package cli.command.hook

import cli.commons.PlatformCommandRunner
import cli.commons.defaultFileSystem
import cli.commons.readText
import cli.commons.writeText
import cli.env.Environment
import cli.env.RealEnvironment
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import formatFullMessage
import okio.FileSystem
import okio.Path.Companion.toPath

internal const val DEFAULT_GIT_MSG_FILE = ".git/EDIT_COMMITMSG"

class FormatFile(
    private val fileSystem: FileSystem = defaultFileSystem,
    private val env: Environment = RealEnvironment(PlatformCommandRunner()),
    private val format: (message: String, commentChar: Char, isMarkdown: Boolean) -> String = ::formatFullMessage,
) : CliktCommand(
    name = "format-file",
    help = "Format the git commit message file (or another file)"
) {

    private val messageFile by argument(
        "file",
        help = "Path to the message file. Default is the standard git file, $DEFAULT_GIT_MSG_FILE"
    ).default(DEFAULT_GIT_MSG_FILE)

    private val isMarkdown by option(
        "--markdown",
        help = "Set when message is in Markdown format. Default: false."
    ).flag()

    override fun run() {
        try {
            val file = messageFile.toPath()
            val content = file.readText(fileSystem)
            val formattedContent = format(content, env.gitCommentChar(), isMarkdown)
            file.writeText(formattedContent, fileSystem)
        } catch (error: IllegalArgumentException) {
            throw PrintMessage(error.message.orEmpty(), error = true)
        }
    }
}