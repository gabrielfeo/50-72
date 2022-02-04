package aggregate

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import java.io.File
import javax.inject.Inject

abstract class Aggregate : DefaultTask() {

    @get:Inject
    protected abstract val fs: FileSystemOperations

    @get:OutputDirectory
    protected abstract val destinationDirectory: DirectoryProperty

    @get:InputFiles
    protected abstract val files: ConfigurableFileCollection

    var destinationDir: File
        get() = destinationDirectory.get().asFile
        set(file: File) = destinationDirectory.set(file)

    fun includePath(path: String) {
        files.from(path)
    }

    fun includeOutputsOf(tasks: Iterable<TaskProvider<*>>) {
        files.from(tasks)
    }

    @TaskAction
    protected fun aggregate() {
        fs.copy {
            from(files)
            into(destinationDirectory)
        }
    }

}