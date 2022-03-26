import cli.command.FormatMessage
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktConsole
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private const val ERROR_MESSAGE = "formatOut"

class FormatMessageTest {

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
    fun givenFormatThrowsThenThrowUsageError() {
        val error = assertFailsWith<UsageError> {
            run("message", formatThrows = true)
        }
        assertEquals(ERROR_MESSAGE, error.message)
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
        FormatMessage(
            format = { message, isMarkdown ->
                formatArgs.isMarkdown = isMarkdown
                formatArgs.message = message
                if (formatThrows) throw IllegalArgumentException(ERROR_MESSAGE)
                else message
            }
        ).context {
            console = object : CliktConsole {
                override fun promptForLine(prompt: String, hideInput: Boolean) = TODO()
                override val lineSeparator = "\n"
                override fun print(text: String, error: Boolean) = when {
                    error -> stderr += text
                    else -> stdout += text
                }
            }
        }.parse(args as Array<String>)
    }
}