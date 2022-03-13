import okio.FileSystem
import okio.Path
import okio.buffer
import okio.use

fun Path.exists(fileSystem: FileSystem): Boolean = fileSystem.exists(this)

fun Path.readText(fileSystem: FileSystem): String {
    fileSystem.source(this).use { fileSource ->
        fileSource.buffer().use { bufferedFileSource ->
            return buildString {
                while (true) {
                    val line = bufferedFileSource.readUtf8Line() ?: break
                    appendLine(line)
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

fun Path.writeText(text: String, fileSystem: FileSystem, mustCreate: Boolean = true) {
    fileSystem.write(this, mustCreate) {
        writeUtf8(text)
    }
}
