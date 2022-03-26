package cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import formatFullMessage

class FormatMessage(
    private val format: (message: String, isMarkdown: Boolean) -> String = ::formatFullMessage,
) : CliktCommand(name = "format") {

    private val message by argument(help = "Commit message to be formatted")

    private val isMarkdown by option("--markdown", help = "Set when message is in Markdown format")
        .flag(default = false)

    override fun run() {
        try {
            val formattedMessage = format(message, isMarkdown)
            echo(formattedMessage)
        } catch (error: IllegalArgumentException) {
            throw UsageError(error.message.orEmpty(), paramName = "message")
        }
    }
}

