package cli.commons

import platform.posix.*

val PermissionSet.mode: UShort
    get() = when (this) {
        PermissionSet.`755` -> S_IRWXU.or(S_IRGRP).or(S_IXGRP).or(S_IXOTH).toUShort()
    }

actual fun createFilePermissionSetter(): FilePermissionSetter = ChmodFilePermissionSetter(
    chmod = { path, permissions -> chmod(path, permissions.mode) },
    errno = { errno }
)
