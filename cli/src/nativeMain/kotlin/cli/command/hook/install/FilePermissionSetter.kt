package cli.command.hook.install

import platform.posix.*

@Suppress("EnumEntryName")
enum class PermissionSet(internal val mode: mode_t) {
    `755`(mode = S_IRWXU.or(S_IRGRP).or(S_IXGRP).or(S_IXOTH).toUShort()),
}

fun interface FilePermissionSetter {
    fun set(path: String, permissions: PermissionSet)
}

class Chmod : FilePermissionSetter {

    override fun set(path: String, permissions: PermissionSet) {
        val result = chmod(path, permissions.mode)
        if (result != 0) {
            error("Failed to set ${permissions.name} permissions on $path: errno=$errno")
        }
    }
}
