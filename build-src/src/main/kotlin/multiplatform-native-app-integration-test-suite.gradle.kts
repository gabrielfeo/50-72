/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithTests
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    id("multiplatform-native-app")
}

kotlin {
    val commonMain = kotlin.sourceSets.getByName("commonMain")
    val commonIntegrationTest = sourceSets.create("commonIntegrationTest") {
        requiresVisibilityOf(commonMain)
    }
    targets.filterIsInstance<KotlinNativeTargetWithTests<*>>().forEach {
        val compilation = it.createIntegrationTestCompilation(commonIntegrationTest, commonMain)
        it.createIntegrationTestBinary(compilation)
        it.createIntegrationTestRun()
    }
}

fun KotlinNativeTarget.createIntegrationTestCompilation(
    commonIntegrationTest: KotlinSourceSet,
    commonMain: KotlinSourceSet,
): KotlinNativeCompilation {
    val mainCompilation = compilations.getByName("main")
    return compilations.create("integrationTest") {
        associateWith(mainCompilation)
        defaultSourceSet {
            dependsOn(commonIntegrationTest)
            requiresVisibilityOf(commonMain)
            requiresVisibilityOf(commonIntegrationTest)
            requiresVisibilityOf(mainCompilation.defaultSourceSet)
        }
    }
}

fun KotlinNativeTarget.createIntegrationTestBinary(
    compilation: KotlinNativeCompilation,
) {
    binaries {
        test("integration", listOf(NativeBuildType.DEBUG)) {
            this.compilation = compilation
        }
    }
}

fun KotlinNativeTargetWithTests<*>.createIntegrationTestRun() {
    testRuns {
        create("integration") {
            val integrationTestBinary = binaries.getTest("integration", NativeBuildType.DEBUG)
            setExecutionSourceFrom(integrationTestBinary)
        }
    }
}
