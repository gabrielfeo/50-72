import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertEquals

class HookTest {

    private val fileSystem = FakeFileSystem().apply {
        emulateUnix()
        createDirectories(".git/hooks".toPath())
    }
    private val hook = Hook(
        fileSystem,
        permissionSetter = { _, _ -> }
    )

    private val prepareCommitMsg = PREPARE_COMMIT_MSG_PATH.toPath()

    @Test
    fun givenHookExists_WhenInstall_ThenAppendsCommandToHook() {
        val previousContent = """
                $SHEBANG
                
                echo
                
            """.trimIndent()
        givenHookExists(previousContent)
        install()
        assertEquals(
            """
                $SHEBANG
                
                echo
                
                $FORMAT_FILE_COMMAND
                
            """.trimIndent(),
            prepareCommitMsg.readText(fileSystem),
        )
    }

    private fun install() {
        hook.parse(arrayOf("--install"))
    }

    @Test
    fun givenNoHook_WhenInstall_ThenCreatesHookWithCommandAndShebang() {
        install()
        assertEquals(
            expected = """
                $SHEBANG
                
                $FORMAT_FILE_COMMAND
                
            """.trimIndent(),
            actual = prepareCommitMsg.readText(fileSystem),
        )
    }

    @Test
    fun givenHookExistsWithOtherCommands_WhenUninstall_ThenRemovesOurCommand() {
        TODO()
    }

    @Test
    fun givenHookExistsWithOnlyOurCommand_WhenUninstall_ThenDeletesHook() {
        TODO()
    }

    private fun givenHookExists(content: String) {
        prepareCommitMsg.writeText(content, mustCreate = true, fileSystem = fileSystem)
    }
}