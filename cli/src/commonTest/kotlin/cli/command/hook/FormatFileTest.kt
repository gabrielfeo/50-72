package cli.command.hook

import cli.commons.readText
import cli.commons.writeText
import com.github.ajalt.clikt.core.PrintMessage
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class FormatFileTest {

    private val fileSystem = FakeFileSystem().apply {
        emulateUnix()
    }

    @Test
    fun givenMessageWithNoErrorsThenWritesFormattedMessage() {
        val file = "./msgfile"
        file.toPath().writeText("""Subject\n\nBody""", fileSystem)

        run(file, formatReturnValue = "formatted msg")

        assertEquals("formatted msg", file.toPath().readText(fileSystem))
    }

    @Test
    fun givenFormatFailsThenFailsWithFormatErrorMessage() {
        val file = "./msgfile"
        file.toPath().writeText("any", fileSystem)

        val error = assertFailsWith(PrintMessage::class) {
            run(file, formatErrorMessage = "123")
        }

        assertEquals("123", error.message)
    }

    @Test
    fun givenFormatFailsThenDoesntWriteToFile() {
        val file = "./msgfile"
        file.toPath().writeText("any", fileSystem)

        assertFails {
            run(file, formatErrorMessage = "any error")
        }

        assertEquals("any", file.toPath().readText(fileSystem))
    }

    @Test
    fun givenNoMessageArgumentThenUsesGitCommitMsgFile() {
        fileSystem.createDirectory(".git".toPath())
        DEFAULT_GIT_MSG_FILE.toPath().writeText("msg", fileSystem)

        run(formatReturnValue = "formatted msg")

        assertEquals("formatted msg", DEFAULT_GIT_MSG_FILE.toPath().readText(fileSystem))
    }

    @Suppress("UNCHECKED_CAST")
    private fun run(
        vararg args: String,
        formatReturnValue: String = "",
        formatErrorMessage: String? = null,
    ) {
        FormatFile(
            fileSystem,
            format = { _, _ ->
                formatErrorMessage?.let { throw IllegalArgumentException(it) }
                    ?: formatReturnValue
            }
        ).parse(args as Array<String>)
    }
}
