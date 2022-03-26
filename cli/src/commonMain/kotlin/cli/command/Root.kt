package cli.command

import cli.command.hook.FormatFile
import cli.command.hook.install.Hook
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands

class Root : NoOpCliktCommand(
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
            FormatMessage(),
            FormatFile(),
            Hook(),
        )
    }
}
