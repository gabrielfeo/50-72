/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.command.hook

import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.UsageError
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.*

class HookCommandTest {

    private val fileSystem = FakeFileSystem().apply {
        emulateUnix()
    }

    private val installAction = object : InstallAction {
        var called = false
        override fun invoke(markdownFormat: Boolean) {
            called = true
        }
    }

    private val uninstallAction = object : UninstallAction {
        var called = false
        override fun invoke() {
            called = true
        }
    }

    private val hook = HookCommand(
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
        val error = assertFailsWith(CliktError::class) {
            requestInstall()
        }
        assertIsGitDirError(error)
    }

    @Test
    fun givenCurrentDirIsNotGitDir_whenUninstall_ThenPrintsError() {
        val error = assertFailsWith(CliktError::class) {
            requestUninstall()
        }
        assertIsGitDirError(error)
    }

    @Test
    fun whenBothInstallAndUninstall_ThenPrintsError() {
        assertFailsWith(CliktError::class) {
            requestBoth()
        }
    }

    @Test
    fun whenNoAction_ThenPrintsError() {
        assertFailsWith(CliktError::class) {
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