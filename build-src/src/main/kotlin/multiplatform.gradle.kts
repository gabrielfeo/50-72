import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

plugins {
    kotlin("multiplatform")
}


configure<KotlinMultiplatformExtension> {
    // Use locally better debugging support
    if (shouldEnableJvmTarget()) {
        jvm()
    }
}

if (shouldShowTestStandardStreams()) {
    tasks.withType<Test>().configureEach {
        testLogging {
            showStandardStreams = true
        }
    }
}

fun Project.shouldEnableJvmTarget(): Boolean =
    providers.gradleProperty("com.gabrielfeo.5072.jvmTarget").orNull?.toBoolean() ?: false

fun Project.shouldShowTestStandardStreams(): Boolean =
    providers.gradleProperty("com.gabrielfeo.5072.showTestStandardStreams").orNull?.toBoolean() ?: false
