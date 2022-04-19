/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet


plugins {
    id("multiplatform-native-app")
}

kotlin {
    val posixMain = configureSharedPosixSourceSet()
    sourceSets
        .filter { it.name.startsWith("macos") || it.name.startsWith("linux") }
        .filter { it.name.endsWith("Main") }
        .forEach { it.dependsOn(posixMain) }
}

fun KotlinMultiplatformExtension.configureSharedPosixSourceSet(): KotlinSourceSet {
    val commonMain = sourceSets.getByName("commonMain")
    return sourceSets.create("posixMain").apply {
        dependsOn(commonMain)
        requiresVisibilityOf(commonMain)
    }
}
