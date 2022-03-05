import platform.posix.exit
import platform.posix.fprintf
import platform.posix.stderr

fun main(args: Array<String>) {
    runCommand(
        args,
        format = ::formatFullMessage,
        printStdout = ::println,
        printStderr = ::printError,
        exit = ::exit,

    )
}

private fun printError(message: String?) {
    if (message.isNullOrBlank())
        return
    fprintf(stderr, "$message\n")
}
