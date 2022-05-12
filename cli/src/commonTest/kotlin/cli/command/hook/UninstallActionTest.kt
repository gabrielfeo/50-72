/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.command.hook

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
        echo = {},
    )

    @Test
    fun givenHookExistsWithOtherCommands_ThenRemovesOurCommand() {
        givenHookExists(
            """
                $SHEBANG
                
                echo
                
                $FORMAT_FILE_AS_PLAIN_TEXT_COMMAND
                
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
                
                $FORMAT_FILE_AS_PLAIN_TEXT_COMMAND
                
            """.trimIndent()
        )
        action()
        assertHookDoesntExist()
    }

    @Test
    fun givenHookExistsWithFormatAsMarkdownCommand_ThenRemovesCommand() {
        givenHookExists(
            """
                $SHEBANG
                
                echo
                
                $FORMAT_FILE_AS_MARKDOWN_COMMAND
                
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