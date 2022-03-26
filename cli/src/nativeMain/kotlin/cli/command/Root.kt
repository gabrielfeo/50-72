package cli.command

import cli.command.hook.FormatFile
import cli.command.hook.install.Hook
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands

val rootCommand = NoOpCliktCommand()
    .subcommands(
        FormatMessage(),
        FormatFile(),
        Hook(),
    )
