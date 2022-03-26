package cli.commons

import okio.*

fun Path.exists(fileSystem: FileSystem): Boolean =
    fileSystem.exists(this)

fun Path.delete(fileSystem: FileSystem, mustExist: Boolean = true) {
    fileSystem.delete(this, mustExist)
}

fun Path.readText(fileSystem: FileSystem): String {
    return withBufferedSource(fileSystem) {
        readUtf8()
    }
}

fun Path.readLines(fileSystem: FileSystem): Sequence<String> {
    return withBufferedSource(fileSystem) {
        generateSequence(::readUtf8Line)
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

private inline fun <T> Path.withBufferedSource(fileSystem: FileSystem, block: BufferedSource.() -> T): T {
    fileSystem.source(this).use { fileSource ->
        fileSource.buffer().use { bufferedSource ->
            return bufferedSource.block()
        }
    }
}
