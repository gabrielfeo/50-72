package cli.commons

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
            error("Failed to set ${permissions.name} permissions on $path: errno=${errno()}")
        }
    }
}
