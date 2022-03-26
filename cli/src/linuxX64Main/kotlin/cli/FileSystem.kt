package cli

import okio.FileSystem

actual val defaultFileSystem: FileSystem get() = FileSystem.SYSTEM
