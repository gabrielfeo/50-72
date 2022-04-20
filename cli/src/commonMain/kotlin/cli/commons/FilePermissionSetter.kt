/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

package cli.commons

import com.github.ajalt.clikt.core.CliktError

interface FilePermissionSetter {
    fun set(path: String, permissions: PermissionSet)
}

@Suppress("EnumEntryName")
enum class PermissionSet {
    `755`,
}

expect fun createFilePermissionSetter(): FilePermissionSetter

class ChmodFilePermissionSetter(
    private val chmod: (path: String, mode: PermissionSet) -> Int,
    private val errno: () -> Int,
) : FilePermissionSetter {

    override fun set(path: String, permissions: PermissionSet) {
        val result = chmod(path, permissions)
        if (result != 0) {
            throw CliktError("Failed to set ${permissions.name} permissions on $path: errno=${errno()}")
        }
    }
}
