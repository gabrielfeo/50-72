import cli.commons.PlatformCommandRunner
import cli.commons.WorkDir
import cli.commons.defaultFileSystem
import okio.FileSystem
import okio.IOException
import okio.Path
import kotlin.test.Test

class RealEnvironmentIntegrationTest {

    val tempDir = tempDir()

    @Test
    fun test() {
        val (status, stdout) = PlatformCommandRunner(tempDir.toWorkDir()).run("pwd && echo hello")
        println("Result: [$status] [$stdout]")
    }
}

val realFileSystem get() = defaultFileSystem

fun Path.toWorkDir() = WorkDir(this.toString())

fun Any.tempDir(): Path {
    val rootTempDir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY
    val tempDirForClass = rootTempDir.resolve(this::class.simpleName!!)
    try {
        realFileSystem.createDirectories(tempDirForClass, mustCreate = true)
    } catch (e: IOException) {
        if ("already exist" in e.message.orEmpty()) {
            throw IllegalStateException("Some class isn't cleaning up its tempDir after tests", e)
        } else {
            throw e
        }
    }
    return tempDirForClass
}
