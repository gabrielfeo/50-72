import kotlin.test.Test
import kotlin.test.assertEquals

private const val ERROR_MESSAGE = "formatOut"

class MainTest {
    private var exitCode = 0
    private var stdout = ""
    private var stderr = ""
    private val formatArgs = object {
        var message: String? = null
        var isMarkdown: Boolean? = null
    }

    @Test
    fun givenFormatReturnsThenPrintStdoutAndExit0() {
        run("message")
        assertEquals("message\n", stdout)
        assertEquals(0, exitCode)
        assert(stderr.isEmpty())
    }

    @Test
    fun givenFormatThrowsThenPrintErrorAndExit1() {
        run("message", formatThrows = true)
        assertEquals("$ERROR_MESSAGE\n", stderr)
        assertEquals(1, exitCode)
        assert(stdout.isEmpty())
    }

    @Test
    fun isMarkdownFalseByDefault() {
        run("message")
        assertEquals(false, formatArgs.isMarkdown)
    }

    @Test
    fun usesCommandArgsInFormat() {
        run("message", "--markdown")
        assertEquals(true, formatArgs.isMarkdown)
        assertEquals("message", formatArgs.message)
    }

    @Test
    fun acceptsArgumentOrdering1() {
        assert(formatArgs.message == null) // TODO Remove
        run("message", "--markdown")
        assertEquals(true, formatArgs.isMarkdown)
        assertEquals("message", formatArgs.message)
    }

    @Test
    fun acceptsArgumentOrdering2() {
        assert(formatArgs.message == null) // TODO Remove
        run("--markdown", "message")
        assertEquals(true, formatArgs.isMarkdown)
        assertEquals("message", formatArgs.message)
    }

    @Suppress("UNCHECKED_CAST")
    private fun run(vararg args: String, formatThrows: Boolean = false) {
        runCommand(
            args as Array<String>,
            format = { message, isMarkdown ->
                formatArgs.isMarkdown = isMarkdown
                formatArgs.message = message
                if (formatThrows) error(ERROR_MESSAGE)
                else message
            },
            printStdout = { stdout += "$it\n" },
            printStderr = { stderr += "$it\n" },
            exit = { exitCode = it },
        )
    }
}