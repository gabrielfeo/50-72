package cli.commons

import okio.FileSystem

actual val defaultFileSystem: FileSystem get() = FileSystem.SYSTEM
