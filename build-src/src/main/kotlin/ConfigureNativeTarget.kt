/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

private const val LINUX = "Linux"
private const val MAC_OS = "Mac OS X"

fun Project.configureNativeTargets(
    configure: KotlinNativeTargetWithHostTests.() -> Unit
) {
    configure<KotlinMultiplatformExtension> {
        val posixMain = configureSharedPosixSourceSet()
        configureTargets(this, posixMain, configure)
    }
}

private fun KotlinMultiplatformExtension.configureSharedPosixSourceSet(): KotlinSourceSet {
    val commonMain = sourceSets.getByName("commonMain")
    return sourceSets.create("posixMain").apply {
        dependsOn(commonMain)
    }
}

private fun Project.configureTargets(
    extension: KotlinMultiplatformExtension,
    posixMain: KotlinSourceSet,
    configure: KotlinNativeTargetWithHostTests.() -> Unit,
) {
    extension.apply {
        val hostOs = hostOs().also {
            check(it == LINUX || it == MAC_OS) { "OS '$it' not supported" }
        }
        val configureAll = shouldConfigureAllTargets()
        if (hostOs == LINUX || configureAll)
            configureLinuxTargets(posixMain, configure)
        if (hostOs == MAC_OS || configureAll)
            configureMacOsTargets(posixMain, configure)
    }
}

private fun KotlinMultiplatformExtension.configureMacOsTargets(
    posixMain: KotlinSourceSet,
    configure: KotlinNativeTargetWithHostTests.() -> Unit,
) {
    macosX64(configure = configure)
    sourceSets.getByName("macosX64Main").dependsOn(posixMain)
}

private fun KotlinMultiplatformExtension.configureLinuxTargets(
    posixMain: KotlinSourceSet,
    configure: KotlinNativeTargetWithHostTests.() -> Unit,
) {
    linuxX64(configure = configure)
    sourceSets.getByName("linuxX64Main").dependsOn(posixMain)
}

private fun Project.hostOs(): String? =
    providers.systemProperty("os.name").orNull

private fun Project.shouldConfigureAllTargets(): Boolean =
    providers.gradleProperty("configureAllTargets").isPresent
