/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import java.nio.file.Files

plugins {
    id("multiplatform-native-app")
}

val releaseDir = project.layout.buildDirectory.dir("release").get()

val packageRelease by tasks.registering task@{
    group = "Release"
    description = "Links all enabled release executables and archives each in its own zip"
}

val buildLatestRelease by tasks.registering task@{
    group = "Release"
    description = "Links all enabled release executables and symlinks them to the 'latest' folder"
}

kotlin.targets.configureEach {
    if (platformType != KotlinPlatformType.native) {
        return@configureEach
    }
    configureTasksForTarget(this)
}

fun configureTasksForTarget(target: KotlinTarget) {
    val symlinkTargetRelease = registerSymlinkTaskFor(target)
    buildLatestRelease.configure {
        val targetSymlink = symlinkTargetRelease.map { it.outputs }
        inputs.file(targetSymlink)
        outputs.file(targetSymlink)
    }
    val packageTargetRelease = registerPackageTaskFor(target)
    packageRelease.configure {
        val targetPackage = packageTargetRelease.map { it.outputs }
        inputs.file(targetPackage)
        outputs.file(targetPackage)
    }
}

fun registerSymlinkTaskFor(target: KotlinTarget) =
    tasks.register("symlinkExecutable${target.name.capitalized()}") {
        val outputFolder = releaseDir.dir("latest/${target.name}/bin")
        val symlinkFile = outputFolder.file("50-72")
        val kexe = linkReleaseExecutable(target).map { linkage ->
            val executableDir = linkage.outputs.files.singleFile.toPath()
            executableDir.resolve("cli.kexe")
        }
        outputs.file(symlinkFile)
        inputs.file(kexe)
        doLast {
            val linkPath = symlinkFile.asFile.toPath()
            Files.createSymbolicLink(linkPath, kexe.get())
        }
    }

fun registerPackageTaskFor(target: KotlinTarget) =
    tasks.register<Zip>("packageRelease${target.name.capitalized()}") {
        with(executableCopySpecFor(target, intoDir = "50-72/bin"))
        destinationDirectory.set(releaseDir)
        archiveBaseName.set("50-72")
        archiveVersion.set("v${project.version}")
        archiveClassifier.set(target.targetName)
    }

fun executableCopySpecFor(target: KotlinTarget, intoDir: String) = copySpec {
    val executableLinkage = linkReleaseExecutable(target)
    from(executableLinkage.map { it.outputs })
    include { it.path.endsWith(".kexe") }
    rename(".*", "50-72")
    into(intoDir)
}

fun linkReleaseExecutable(target: KotlinTarget) =
    tasks.named("linkReleaseExecutable${target.name.capitalized()}")
