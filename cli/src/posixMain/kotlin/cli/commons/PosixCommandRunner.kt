/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package cli.commons

import cli.commons.CommandRunner.Result
import cli.commons.CommandRunner.StdOut
import kotlinx.cinterop.refTo
import kotlinx.cinterop.toKString
import platform.posix.errno
import platform.posix.fgets
import platform.posix.pclose
import platform.posix.popen

actual fun PlatformCommandRunner(
    workDir: WorkDir,
): CommandRunner = PosixCommandRunner(workDir)

class PosixCommandRunner(
    private val workDir: WorkDir,
) : CommandRunner {

    /**
     * Based on https://stackoverflow.com/a/57124947/7546633
     */
    override fun run(
        command: String,
    ): Result {
        val finalCommand = when (workDir) {
            WorkDir(".") -> command
            else -> "(cd ${workDir.path}; $command)"
        }
        val stdoutFile = popen(finalCommand, "r") ?: errorOpeningSubprocess(finalCommand)
        val stdout = buildString {
            val buffer = ByteArray(4096)
            while (true) {
                val input = fgets(buffer.refTo(0), buffer.size, stdoutFile) ?: break
                append(input.toKString())
            }
        }
        val status = pclose(stdoutFile)
        return Result(status, StdOut(stdout))
    }

    private fun errorOpeningSubprocess(command: String): Nothing =
        error("Failed open subprocess (errno=$errno): $command")
}
