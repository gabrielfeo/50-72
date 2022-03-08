import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) {
    Main().subcommands(
        InstallHook(),
    ).main(args)
}
