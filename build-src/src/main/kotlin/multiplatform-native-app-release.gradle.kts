import org.jetbrains.kotlin.gradle.plugin.KotlinTarget

plugins {
    id("multiplatform-native-app")
}

val packageRelease by tasks.registering(Zip::class) {
    group = "Release"
}

kotlin.targets.configureEach {
    if ("metadata" in name) {
        return@configureEach
    }
    val targetRelease = createPackageTaskFor(this)
    packageRelease.configure {
        dependsOn(targetRelease)
    }
}

fun createPackageTaskFor(target: KotlinTarget): TaskProvider<Zip> {
    val suffix = target.name.capitalize()
    return tasks.register<Zip>("packageRelease$suffix") {
        val executableLinkage = tasks.named("linkReleaseExecutable$suffix")
        from(executableLinkage.map { it.outputs })
        into("50-72/bin")
        include { it.path.endsWith(".kexe") }
        rename(".*", "50-72")

        val releaseDir = project.layout.buildDirectory.dir("release")
        destinationDirectory.set(releaseDir)

        archiveBaseName.set("50-72")
        archiveVersion.set("v${project.version}")
        archiveClassifier.set(target.targetName)
    }
}
