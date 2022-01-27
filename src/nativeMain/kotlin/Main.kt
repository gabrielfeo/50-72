import platform.posix.exit

fun main(args: Array<String>) {
    try {
        require(args.size == 1) { "Expected args: <commit-message>" }
        val message = args.first()
        print(format(message))
    } catch (error: Throwable) {
        println(error.message)
        exit(1)
    }
}