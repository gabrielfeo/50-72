import org.gradle.testing.base.plugins.TestingBasePlugin.TESTS_DIR_NAME
import org.gradle.testing.base.plugins.TestingBasePlugin.TEST_RESULTS_DIR_NAME
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

// TODO commonIntegrationTest source set
// TODO commonIntegrationTestImplementation
// TODO In convention plugin
configureIntegrationTestSuite()
registerIntegrationTestConfigurations()

fun Project.registerIntegrationTestConfigurations() {
    val implementation = configurations.register("integrationTestImplementation")
    val api = configurations.register("integrationTestApi")
    configurations.configureEach {
        when {
            name.endsWith("IntegrationTestApi") -> extendsFrom(api.get())
            name.endsWith("IntegrationTestImplementation") -> extendsFrom(implementation.get(), api.get())
        }
    }
}

fun Project.configureIntegrationTestSuite() {
    // Can't configureEach because creating a compilation adds a Project.afterEvaluate, which
    // is disallowed by the time the objects are configured
    kotlin.targets.filterIsInstance<KotlinNativeTarget>().forEach {
        val compilation = it.createIntegrationTestCompilation()
        it.configureIntegrationTestBinaryAndTask(project, compilation)
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

fun KotlinNativeTarget.createIntegrationTestCompilation(): KotlinNativeCompilation {
    val mainCompilation = compilations.getByName("main")
    return compilations.create("integrationTest") {
        associateWith(mainCompilation)
        defaultSourceSet {
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
    add("integrationTestImplementation", kotlin("test"))
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