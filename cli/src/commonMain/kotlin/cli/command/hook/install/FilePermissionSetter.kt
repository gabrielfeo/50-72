package cli.command.hook.install

interface FilePermissionSetter {
    fun set(path: String, permissions: PermissionSet)
}

@Suppress("EnumEntryName")
enum class PermissionSet {
    `755`,
}

expect class Chmod() : FilePermissionSetter
