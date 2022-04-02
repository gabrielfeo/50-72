/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget

plugins {
    id("multiplatform-native-app")
}

val packageRelease by tasks.registering(Zip::class) task@{
    group = "Release"
}

kotlin.targets.configureEach {
    if (platformType != KotlinPlatformType.native) {
        return@configureEach
    }
    val targetRelease = registerPackageTaskFor(this)
    packageRelease.configure {
        dependsOn(targetRelease)
    }
}

fun registerPackageTaskFor(target: KotlinTarget): TaskProvider<Zip> {
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
