/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

package cli.commons

import platform.posix.*

val PermissionSet.mode: UInt
    get() = when (this) {
        PermissionSet.`755` -> S_IRWXU.or(S_IRGRP).or(S_IXGRP).or(S_IXOTH).toUInt()
    }

actual fun createFilePermissionSetter(): FilePermissionSetter = ChmodFilePermissionSetter(
    chmod = { path, permissions -> chmod(path, permissions.mode) },
    errno = { errno }
)
