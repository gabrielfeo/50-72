import org.gradle.testing.base.plugins.TestingBasePlugin.TESTS_DIR_NAME
import org.gradle.testing.base.plugins.TestingBasePlugin.TEST_RESULTS_DIR_NAME
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
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

// TODO targets.forEach { createIntegrationTestCompilation() }
// TODO commonIntegrationTest source set
// TODO In convention plugin
kotlin {
    macosX64 target@{
        compilations {
            val main = getByName("main")
            val integrationTest = compilations.create("integrationTest") {
                associateWith(main)
                defaultSourceSet {
                    dependencies {
                        implementation(main.compileDependencyFiles + main.output.classesDirs)
                    }
                }
            }
            binaries {
                test("integration", listOf(NativeBuildType.DEBUG)) {
                    compilation = integrationTest
                    tasks.register("${targetName}IntegrationTest", KotlinNativeHostTest::class) {
                        targetName = target.name
                        executable(linkTaskProvider.flatMap { it.outputFile })
                        inputs.file(linkTaskProvider.map { it.outputFile })
                        val resultsDir = project.layout.buildDirectory.dir("$TEST_RESULTS_DIR_NAME/$name/binary")
                        val reportsDir = project.layout.buildDirectory.dir("$TESTS_DIR_NAME/reports/integrationTest")
                        binaryResultsDirectory.set(resultsDir)
                        reports {
                            junitXml.outputLocation.set(resultsDir)
                            html.outputLocation.set(reportsDir)
                        }
                    }
                }
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
    add("macosX64IntegrationTestImplementation", kotlin("test"))
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
