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
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.TestExecutable
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeHostTest

plugins {
    id("multiplatform-native-app")
}

kotlin {
    val commonMain = sourceSets.getByName("commonMain")
    val commonIntegrationTest = sourceSets.create("commonIntegrationTest") {
        dependsOn(commonMain)
    }
    targets.filterIsInstance<KotlinNativeTarget>().forEach {
        val compilation = it.createIntegrationTestCompilation(commonIntegrationTest)
        it.configureIntegrationTestBinaryAndTask(project, compilation)
    }
}

fun KotlinNativeTarget.createIntegrationTestCompilation(
    commonIntegrationTest: KotlinSourceSet,
): KotlinNativeCompilation {
    val mainCompilation = compilations.getByName("main")
    return compilations.create("integrationTest") {
        associateWith(mainCompilation)
        defaultSourceSet {
            dependsOn(commonIntegrationTest)
            addImplementationDependencyOn(mainCompilation)
        }
    }
}

fun KotlinSourceSet.addImplementationDependencyOn(
    compilation: KotlinNativeCompilation,
) {
    dependencies {
        implementation(compilation.compileDependencyFiles + compilation.output.classesDirs)
    }
}

fun KotlinNativeTarget.configureIntegrationTestBinaryAndTask(
    project: Project,
    compilation: KotlinNativeCompilation,
) {
    binaries {
        test("integration", listOf(NativeBuildType.DEBUG)) {
            this.compilation = compilation
            project.registerIntegrationTestTask(compilation.target, testExecutable = this)
        }
    }
}

fun Project.registerIntegrationTestTask(
    target: KotlinNativeTarget,
    testExecutable: TestExecutable,
) {
    tasks.register("${target.name}IntegrationTest", KotlinNativeHostTest::class) {
        targetName = testExecutable.target.name
        executable(testExecutable.linkTaskProvider.flatMap { it.outputFile })
        inputs.file(testExecutable.linkTaskProvider.map { it.outputFile })
        configureTestReports()
    }
}

fun KotlinNativeHostTest.configureTestReports() {
    val resultsDir = project.layout.buildDirectory.dir("${TestingBasePlugin.TEST_RESULTS_DIR_NAME}/$name/binary")
    val reportsDir = project.layout.buildDirectory.dir("${TestingBasePlugin.TESTS_DIR_NAME}/reports/integrationTest")
    binaryResultsDirectory.set(resultsDir)
    reports {
        junitXml.outputLocation.set(resultsDir)
        html.outputLocation.set(reportsDir)
    }
}
