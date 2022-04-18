/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

package cli.command

import cli.commons.PlatformCommandRunner
import cli.env.Environment
import cli.env.RealEnvironment
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import formatFullMessage

class FormatMessage(
    private val env: Environment = RealEnvironment(PlatformCommandRunner()),
    private val format: (message: String, commentChar: Char, isMarkdown: Boolean) -> String = ::formatFullMessage,
) : CliktCommand(
    name = "format",
    help = "Format a message string."
) {

    private val message by argument(help = "Commit message to be formatted")

    private val isMarkdown by option(
        "--markdown",
        help = "Set when message is in Markdown format. Default: false."
    ).flag(default = false)

    override fun run() {
        try {
            val formattedMessage = format(message, env.gitCommentChar(), isMarkdown)
            echo(formattedMessage)
        } catch (error: IllegalArgumentException) {
            throw PrintMessage(error.message.orEmpty(), error = true)
        }
    }
}

