package cli.command.hook.install

import cli.commons.FilePermissionSetter
import cli.commons.PermissionSet
import cli.commons.readText
import cli.commons.writeText
import com.github.ajalt.clikt.core.PrintMessage
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.*

class InstallActionTest {

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

    private val action: InstallAction = InstallActionImpl(
        fileSystem,
        permissionSetter,
    )

    @Test
    fun givenHookExists_ThenAppendsCommandToHook() {
        givenHookExists(
            """
                $SHEBANG
                
                echo
                
            """.trimIndent()
        )
        action()
        assertHookEquals(
            """
                $SHEBANG
                
                echo
                
                $FORMAT_FILE_COMMAND
                
            """.trimIndent()
        )
    }

    @Test
    fun givenNoHook_ThenCreatesHookWithCommandAndShebang() {
        action()
        assertHookEquals(
            """
                $SHEBANG
                
                $FORMAT_FILE_COMMAND
                
            """.trimIndent()
        )
    }

    @Test
    fun givenNoHook_ThenTriesSettingFilePermissions() {
        action()
        assertTrue(permissionSetter.called)
    }

    @Test
    fun givenNoHook_AndSetPermissionsFails_ThenPrintsError() {
        permissionSetter.shouldFail = true
        val exception = assertFails {
            action()
        }
        assertIs<PrintMessage>(exception)
        assertEquals(FAILED_TO_SET_PERMISSIONS_MSG, exception.message)
    }

    @Test
    fun givenHookExists_ThenDoesntTrySettingFilePermissions() {
        givenHookExists("")
        action()
        assertFalse(permissionSetter.called)
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
}