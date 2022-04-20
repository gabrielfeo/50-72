/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.commons

import cli.commons.CommandRunner.Exit
import cli.commons.CommandRunner.StdOut
import kotlinx.cinterop.refTo
import kotlinx.cinterop.toKString
import platform.posix.errno
import platform.posix.fgets
import platform.posix.pclose
import platform.posix.popen

actual val defaultCommandRunner: CommandRunner = PosixCommandRunner()

class PosixCommandRunner : CommandRunner {

    /**
     * Based on https://stackoverflow.com/a/57124947/7546633
     */
    override fun run(
        command: String,
    ): Exit {
        val stdoutFile = popen(command, "r") ?: errorOpeningSubprocess(command)
        val stdout = buildString {
            val buffer = ByteArray(4096)
            while (true) {
                val input = fgets(buffer.refTo(0), buffer.size, stdoutFile) ?: break
                append(input.toKString())
            }
        }
        val status = pclose(stdoutFile).let {
            // To get the real status in the absence of WEXITSTATUS, divide by 256
            it / 256
        }
        return Exit(status, StdOut(stdout))
    }

    private fun errorOpeningSubprocess(command: String): Nothing =
        error("Failed open subprocess (errno=$errno): $command")
}
