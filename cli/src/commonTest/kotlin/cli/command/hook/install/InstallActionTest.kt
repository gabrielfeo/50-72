/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

package cli.command.hook.install

import cli.commons.FilePermissionSetter
import cli.commons.PermissionSet
import cli.commons.readText
import cli.commons.writeText
import com.github.ajalt.clikt.core.CliktError
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
                
                $FORMAT_FILE_AS_PLAIN_TEXT_COMMAND
                
            """.trimIndent()
        )
    }

    @Test
    fun givenNoHook_ThenCreatesHookWithCommandAndShebang() {
        action()
        assertHookEquals(
            """
                $SHEBANG
                
                $FORMAT_FILE_AS_PLAIN_TEXT_COMMAND
                
            """.trimIndent()
        )
    }

    @Test
    fun givenMarkdownFormatSet_ThenAddsFormatAsMarkdownCommand() {
        action(markdownFormat = true)
        assertHookEquals(
            """
                $SHEBANG
                
                $FORMAT_FILE_AS_MARKDOWN_COMMAND
                
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
        val exception = assertFailsWith(CliktError::class) {
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

    @Test
    fun givenAlreadyInstalledAsPlainText_WhenInstallAsPlainText_ThenDoesNotInstall() {
        givenHookExists("""
            $SHEBANG
            
            $FORMAT_FILE_AS_PLAIN_TEXT_COMMAND
            
        """.trimIndent())
        val thrown = assertFailsWith(PrintMessage::class) {
            action()
        }
        assertEquals(ALREADY_INSTALLED_MSG, thrown.message)
        assertFalse(thrown.error)
    }

    @Test
    fun givenAlreadyInstalledAsMarkdown_WhenInstallAsMarkdown_ThenDoesNotInstall() {
        givenHookExists("""
            $SHEBANG
            
            $FORMAT_FILE_AS_MARKDOWN_COMMAND
            
        """.trimIndent())
        testDoesNotInstall(installMarkdownFormat = true)
    }

    @Test
    fun givenAlreadyInstalledAsMarkdown_WhenInstallAsPlainText_ThenDoesNotInstall() {
        givenHookExists("""
            $SHEBANG
            
            $FORMAT_FILE_AS_MARKDOWN_COMMAND
            
        """.trimIndent())
        testDoesNotInstall(installMarkdownFormat = false)
    }

    private fun testDoesNotInstall(installMarkdownFormat: Boolean) {
        val thrown = assertFailsWith(PrintMessage::class) {
            action(installMarkdownFormat)
        }
        assertEquals(ALREADY_INSTALLED_MSG, thrown.message)
        assertFalse(thrown.error)
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

    private fun action(
        markdownFormat: Boolean = false,
    ) {
        InstallActionImpl(
            fileSystem,
            permissionSetter,
        ).invoke(
            markdownFormat,
        )
    }
}