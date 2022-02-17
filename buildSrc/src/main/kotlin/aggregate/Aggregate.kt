package aggregate

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

open class Aggregate @Inject constructor(
    private val fs: FileSystemOperations,
    objectFactory: ObjectFactory,
) : DefaultTask() {

    @InputFiles
    val files = objectFactory.fileCollection()

    @OutputDirectory
    val destinationDir = objectFactory.fileProperty()

    @TaskAction
    protected fun aggregate() {
        fs.copy {
            from(files)
            into(destinationDir)
        }
    }

}