import org.gradle.testing.base.plugins.TestingBasePlugin.TESTS_DIR_NAME
import org.gradle.testing.base.plugins.TestingBasePlugin.TEST_RESULTS_DIR_NAME
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.TestExecutable
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeHostTest

/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

plugins {
    id("multiplatform-native-app")
    id("multiplatform-native-app-release")
}

version = "0.0.1"

// TODO In convention plugin
configureIntegrationTestSuite()

fun Project.configureIntegrationTestSuite() {
    kotlin {
        val commonSourceSet = sourceSets.create("commonIntegrationTest")
        targets.filterIsInstance<KotlinNativeTarget>().forEach {
            val compilation = it.createIntegrationTestCompilation(commonSourceSet)
            it.configureIntegrationTestBinaryAndTask(project, compilation)
        }
    }
}

fun KotlinNativeTarget.configureIntegrationTestBinaryAndTask(project: Project, compilation: KotlinNativeCompilation) {
    binaries {
        test("integration", listOf(NativeBuildType.DEBUG)) {
            this.compilation = compilation
            project.registerIntegrationTestTask(compilation.target, testExecutable = this)
        }
    }
}

fun Project.registerIntegrationTestTask(target: KotlinNativeTarget, testExecutable: TestExecutable) {
    tasks.register("${target.name}IntegrationTest", KotlinNativeHostTest::class) {
        targetName = testExecutable.target.name
        executable(testExecutable.linkTaskProvider.flatMap { it.outputFile })
        inputs.file(testExecutable.linkTaskProvider.map { it.outputFile })
        val resultsDir = project.layout.buildDirectory.dir("$TEST_RESULTS_DIR_NAME/$name/binary")
        val reportsDir = project.layout.buildDirectory.dir("$TESTS_DIR_NAME/reports/integrationTest")
        binaryResultsDirectory.set(resultsDir)
        reports {
            junitXml.outputLocation.set(resultsDir)
            html.outputLocation.set(reportsDir)
        }
    }
}

fun KotlinNativeTarget.createIntegrationTestCompilation(commonSourceSet: KotlinSourceSet): KotlinNativeCompilation {
    val mainCompilation = compilations.getByName("main")
    return compilations.create("integrationTest") {
        associateWith(mainCompilation)
        defaultSourceSet {
            dependsOn(commonSourceSet)
            mainCompilation.let {
                dependencies.add(
                    implementationConfigurationName,
                    it.compileDependencyFiles + it.output.classesDirs,
                )
            }
        }
    }
}

dependencies {
    commonMainImplementation(project(":formatter"))
    commonMainImplementation("com.github.ajalt.clikt:clikt:3.4.0")
    commonMainImplementation("com.squareup.okio:okio:3.0.0")
    commonTestImplementation(kotlin("test"))
    commonTestImplementation("com.squareup.okio:okio-fakefilesystem:3.0.0")
    add("commonIntegrationTestImplementation", kotlin("test"))
}

//afterEvaluate {
//    kotlin {
//        macosX64 {
//            compilations.map {
//                """
//                ${it.name}:
//                    compileOnlyConfigurationName=${it.compileOnlyConfigurationName}
//                    runtimeOnlyConfigurationName=${it.runtimeOnlyConfigurationName}
//                    compileDependencyConfigurationName=${it.compileDependencyConfigurationName}
//                    runtimeDependencyConfigurationName=${it.runtimeDependencyConfigurationName}
//                """.trimIndent()
//            }.joinToString("\n").let(::println)
//        }
//    }
//}