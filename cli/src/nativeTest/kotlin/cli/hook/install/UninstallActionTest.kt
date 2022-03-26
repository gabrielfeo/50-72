package cli.hook.install

import cli.command.hook.install.*
import cli.commons.readText
import cli.commons.writeText
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class UninstallActionTest {

    private val fileSystem = FakeFileSystem().apply {
        emulateUnix()
        createDirectories(".git/hooks".toPath())
    }

    private val action: UninstallAction = UninstallActionImpl(
        fileSystem,
    )

    @Test
    fun givenHookExistsWithOtherCommands_ThenRemovesOurCommand() {
        givenHookExists(
            """
                $SHEBANG
                
                echo
                
                $FORMAT_FILE_COMMAND
                
            """.trimIndent()
        )
        action()
        assertHookEquals(
            expected = """
                $SHEBANG
                
                echo
                
            """.trimIndent()
        )
    }

    @Test
    fun givenHookExistsWithOnlyOurCommand_ThenDeletesHook() {
        givenHookExists(
            """
                $SHEBANG
                
                $FORMAT_FILE_COMMAND
                
            """.trimIndent()
        )
        action()
        assertHookDoesntExist()
    }

    private fun givenHookExists(content: String) {
        prepareCommitMsg.writeText(content, mustCreate = true, fileSystem = fileSystem)
    }

    private fun assertHookEquals(expected: String) {
        assertEquals(
            expected,
            actual = prepareCommitMsg.readText(fileSystem),
        )
    }

    private fun assertHookDoesntExist() {
        assertFalse(fileSystem.exists(prepareCommitMsg))
    }
}