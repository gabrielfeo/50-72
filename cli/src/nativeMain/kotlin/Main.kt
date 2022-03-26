import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) {
    NoOpCliktCommand().subcommands(
        FormatMessage(),
        FormatFile(),
        Hook(),
    ).main(args)
}
