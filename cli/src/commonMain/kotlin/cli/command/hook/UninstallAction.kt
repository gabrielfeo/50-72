/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.command.hook

import cli.commons.*
import okio.FileSystem

fun interface UninstallAction {
    operator fun invoke()
}

class UninstallActionImpl(
    private val fileSystem: FileSystem = defaultFileSystem,
    private val echo: (msg: String) -> Unit,
) : UninstallAction {

    override fun invoke() {
        prepareCommitMsg.run {
            if (!exists(fileSystem)) {
                echo("Not installed.")
                return
            }

            val hookLines = readLines(fileSystem).toList()
            val containsOurCommandOnly = hookLines.all {
                it.startsWith("#!") || it.startsWith("50-72") || it.isBlank()
            }
            if (containsOurCommandOnly) {
                delete(fileSystem)
            } else {
                val hookWithoutOurCommand = hookLines
                    .filterNot { it.startsWith("50-72") }
                    .joinToString(separator = "\n")
                writeText(hookWithoutOurCommand, fileSystem)
            }
        }
        echo("Successfully uninstalled!")
    }
}