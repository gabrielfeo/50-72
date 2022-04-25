/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.command

import cli.command.format.FormatMessageCommand
import cli.command.format.FormatMessageFileCommand
import cli.command.hook.HookCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands

class RootCommand : NoOpCliktCommand(
    name = "50-72",
    help = """
        Format commit messages to the 50/72 rule automatically.
        
        It's recommended to install it in the git hooks of each repository:
        ```
            50-72 hook --install
        ```
            
        Otherwise, manual usage is:
        ```
            50-72 format MESSAGE (to format a message string)
            50-72 format-file (to format the git commit message file)
        ```
            
        See --help of each subcommand for more.
    """.trimIndent()
) {
    init {
        subcommands(
            FormatMessageCommand(),
            FormatMessageFileCommand(),
            HookCommand(),
        )
    }
}
