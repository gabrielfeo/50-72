package cli.command.hook.install

import platform.posix.*

val PermissionSet.mode: UShort
    get() = when (this) {
        PermissionSet.`755` -> S_IRWXU.or(S_IRGRP).or(S_IXGRP).or(S_IXOTH).toUShort()
    }

actual class Chmod : FilePermissionSetter {

    override fun set(path: String, permissions: PermissionSet) {
        val result = chmod(path, permissions.mode)
        if (result != 0) {
            error("Failed to set ${permissions.name} permissions on $path: errno=$errno")
        }
    }
}