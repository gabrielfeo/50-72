package cli.hook.install

import cli.command.hook.install.Hook
import cli.command.hook.install.InstallAction
import cli.command.hook.install.NOT_A_GIT_DIR_MSG
import cli.command.hook.install.UninstallAction
import com.github.ajalt.clikt.core.UsageError
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.*

class HookTest {

    private val fileSystem = FakeFileSystem().apply {
        emulateUnix()
    }

    private val installAction = object : InstallAction {
        var called = false
        override fun invoke() {
            called = true
        }
    }

    private val uninstallAction = object : UninstallAction {
        var called = false
        override fun invoke() {
            called = true
        }
    }

    private val hook = Hook(
        fileSystem,
        installAction,
        uninstallAction,
    )

    @Test
    fun whenInstallRequested_ThenInvokesInstallAction() {
        givenCurrentDirIsGitDir()
        requestInstall()
        assertTrue(installAction.called)
    }

    @Test
    fun whenUninstallRequested_ThenInvokesUninstallAction() {
        givenCurrentDirIsGitDir()
        requestUninstall()
        assertTrue(uninstallAction.called)
    }

    @Test
    fun givenCurrentDirIsNotGitDir_whenInstall_ThenPrintsError() {
        val error = assertFails {
            requestInstall()
        }
        assertIsGitDirError(error)
    }

    @Test
    fun givenCurrentDirIsNotGitDir_whenUninstall_ThenPrintsError() {
        val error = assertFails {
            requestUninstall()
        }
        assertIsGitDirError(error)
    }

    @Test
    fun whenBothInstallAndUninstall_ThenPrintsError() {
        assertFails {
            requestBoth()
        }
    }

    @Test
    fun whenNoAction_ThenPrintsError() {
        assertFails {
            requestNothing()
        }
    }

    private fun requestInstall() {
        hook.parse(arrayOf("--install"))
    }

    private fun requestUninstall() {
        hook.parse(arrayOf("--uninstall"))
    }

    private fun requestBoth() {
        hook.parse(arrayOf("--install --uninstall"))
    }

    private fun requestNothing() {
        hook.parse(arrayOf(""))
    }

    private fun givenCurrentDirIsGitDir() {
        fileSystem.createDirectories(".git/hooks".toPath())
    }

    private fun assertIsGitDirError(error: Throwable) {
        assertIs<UsageError>(error)
        assertEquals(NOT_A_GIT_DIR_MSG, error.message)
    }
}