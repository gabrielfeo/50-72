package cli.commons

import okio.FileSystem
import okio.Path
import okio.buffer
import okio.use

fun Path.exists(fileSystem: FileSystem): Boolean =
    fileSystem.exists(this)

fun Path.delete(fileSystem: FileSystem, mustExist: Boolean = true) {
    fileSystem.delete(this, mustExist)
}

fun Path.readText(fileSystem: FileSystem): String {
    fileSystem.source(this).use { fileSource ->
        fileSource.buffer().use { bufferedFileSource ->
            return bufferedFileSource.readUtf8()
        }
    }
}

fun Path.readLines(fileSystem: FileSystem): Sequence<String> {
    fileSystem.source(this).use { fileSource ->
        fileSource.buffer().use { bufferedFileSource ->
            return sequence {
                while (true) {
                    yield(bufferedFileSource.readUtf8Line() ?: break)
                }
            }
        }
    }
}

fun Path.appendText(text: String, fileSystem: FileSystem) {
    fileSystem.appendingSink(this).buffer().use {
        it.writeUtf8(text)
    }
}

fun Path.writeText(text: String, fileSystem: FileSystem, mustCreate: Boolean = false) {
    fileSystem.write(this, mustCreate) {
        writeUtf8(text)
    }
}
