/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package cli.commons

expect val defaultCommandRunner: CommandRunner

fun interface CommandRunner {

    fun run(
        command: String,
    ): Exit

    data class Exit(
        val status: Int,
        val stdout: StdOut,
    )

    value class StdOut(val content: String)
}
