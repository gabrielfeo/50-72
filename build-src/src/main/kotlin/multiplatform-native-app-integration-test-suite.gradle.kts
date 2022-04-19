/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.*

plugins {
    id("multiplatform-native-app")
}

kotlin {
    val commonMain = sourceSets.getByName("commonMain")
    val commonIntegrationTest = createCommonIntegrationTestSourceSet(commonMain)
    targets
        .filterIsInstance<KotlinNativeTargetWithTests<*>>()
        .forEach { it.configureIntegrationTest(commonIntegrationTest, commonMain) }
}

fun KotlinMultiplatformExtension.createCommonIntegrationTestSourceSet(
    commonMain: KotlinSourceSet,
): KotlinSourceSet {
    return sourceSets.create("commonIntegrationTest") {
        requiresVisibilityOf(commonMain)
    }
}

fun KotlinNativeTargetWithTests<*>.configureIntegrationTest(
    commonIntegrationTest: KotlinSourceSet,
    commonMain: KotlinSourceSet,
) {
    val compilation = createIntegrationTestCompilation(commonIntegrationTest, commonMain)
    val binary = createIntegrationTestBinary(compilation)
    createIntegrationTestRun(binary)
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
): TestExecutable {
    binaries.test("integration", listOf(NativeBuildType.DEBUG)) {
        this.compilation = compilation
    }
    return binaries.getTest("integration", NativeBuildType.DEBUG)
}

fun KotlinNativeTargetWithTests<*>.createIntegrationTestRun(
    binary: TestExecutable,
) {
    testRuns {
        create("integration") {
            setExecutionSourceFrom(binary)
        }
    }
}
