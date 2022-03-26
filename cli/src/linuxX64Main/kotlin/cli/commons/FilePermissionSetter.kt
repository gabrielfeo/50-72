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
