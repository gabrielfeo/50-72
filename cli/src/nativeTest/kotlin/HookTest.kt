import com.github.ajalt.clikt.core.PrintMessage
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.*

class HookTest {

    private val fileSystem = FakeFileSystem().apply {
        emulateUnix()
        createDirectories(".git/hooks".toPath())
    }

    private val permissionSetter = object : FilePermissionSetter {
        var shouldFail = false
        var called = false
        override fun set(path: String, permissions: PermissionSet) {
            called = true
            if (shouldFail) {
                error("any")
            }
        }
    }

    private val hook = Hook(
        fileSystem,
        permissionSetter,
    )

    private val prepareCommitMsg = PREPARE_COMMIT_MSG_PATH.toPath()

    @Test
    fun givenHookExists_WhenInstall_ThenAppendsCommandToHook() {
        givenHookExists(
            """
                $SHEBANG
                
                echo
                
            """.trimIndent()
        )
        install()
        assertHookEquals(
            """
                $SHEBANG
                
                echo
                
                $FORMAT_FILE_COMMAND
                
            """.trimIndent()
        )
    }

    @Test
    fun givenNoHook_WhenInstall_ThenCreatesHookWithCommandAndShebang() {
        install()
        assertHookEquals(
            """
                $SHEBANG
                
                $FORMAT_FILE_COMMAND
                
            """.trimIndent()
        )
    }

    @Test
    fun givenHookExistsWithOtherCommands_WhenUninstall_ThenRemovesOurCommand() {
        givenHookExists(
            """
                $SHEBANG
                
                echo
                
                $FORMAT_FILE_COMMAND
                
            """.trimIndent()
        )
        uninstall()
        assertHookEquals(
            expected = """
                $SHEBANG
                
                echo
                
            """.trimIndent()
        )
    }

    @Test
    fun givenHookExistsWithOnlyOurCommand_WhenUninstall_ThenDeletesHook() {
        givenHookExists(
            """
                $SHEBANG
                
                $FORMAT_FILE_COMMAND
                
            """.trimIndent()
        )
        uninstall()
        assertHookDoesntExist()
    }

    @Test
    fun givenNoHook_WhenInstall_TriesSettingFilePermissions() {
        install()
        assertTrue(permissionSetter.called)
    }

    @Test
    fun givenNoHook_WhenInstall_AndSetPermissionsFails_PrintsError() {
        permissionSetter.shouldFail = true
        val exception = assertFails {
            install()
        }
        assertIs<PrintMessage>(exception)
        assertEquals(FAILED_TO_SET_PERMISSIONS_MSG, exception.message)
    }

    @Test
    fun givenHookExists_WhenInstall_DoesntTrySettingFilePermissions() {
        givenHookExists("")
        install()
        assertFalse(permissionSetter.called)
    }

    private fun givenHookExists(content: String) {
        prepareCommitMsg.writeText(content, mustCreate = true, fileSystem = fileSystem)
    }

    private fun install() {
        hook.parse(arrayOf("--install"))
    }

    private fun uninstall() {
        hook.parse(arrayOf("--uninstall"))
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