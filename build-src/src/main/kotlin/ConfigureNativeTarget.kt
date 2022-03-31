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
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

private const val LINUX = "Linux"
private const val MAC_OS = "Mac OS X"

fun Project.configureNativeTargets(
    configure: KotlinNativeTargetWithHostTests.() -> Unit
) {
    configure<KotlinMultiplatformExtension> {
        val hostOs = hostOs().also {
            check(it == LINUX || it == MAC_OS) { "OS '$it' not supported" }
        }
        val configureAll = shouldConfigureAllTargets()
        if (hostOs == LINUX || configureAll)
            linuxX64(configure = configure)
        if (hostOs == MAC_OS || configureAll)
            macosX64(configure = configure)
    }
}

private fun Project.hostOs(): String? =
    providers.systemProperty("os.name").orNull

private fun Project.shouldConfigureAllTargets(): Boolean =
    providers.gradleProperty("configureAllTargets").isPresent
