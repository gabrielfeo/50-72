import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

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

dependencies {
    commonMainImplementation(project(":formatter"))
    commonMainImplementation("com.github.ajalt.clikt:clikt:3.4.0")
    commonMainImplementation("com.squareup.okio:okio:3.0.0")
    commonTestImplementation(kotlin("test"))
    commonTestImplementation("com.squareup.okio:okio-fakefilesystem:3.0.0")
}

// TODO targets.forEach { createIntegrationTestCompilation() }
// TODO commonIntegrationTest source set
// TODO In convention plugin
kotlin {
    macosX64 target@{
        compilations {
            val main = getByName("main")
            val integrationTest = compilations.create("integrationTest") {
                defaultSourceSet {
                    dependencies {
                        implementation(main.compileDependencyFiles + main.output.classesDirs)
                        implementation(kotlin("test"))
                    }
                }
            }
            binaries {
                test("integrationTest", listOf(NativeBuildType.DEBUG)) {
                    compilation = integrationTest
                }
            }
        }
    }
}

afterEvaluate {
//    tasks.getByName("macosX64Test", KotlinNativeHostTest::class) {
//        println("Task '$path': [${this.executable},]")
//    }
    kotlin {
        macosX64 {
            println(binaries.joinToString { it.toString() + "=" + it.linkTaskName })
            println("[" + compilations.joinToString() + "]")
            println(compilations.getByName("test").binariesTaskName)
            println(compilations.getByName("test").attributes)
            println(compilations.getByName("test").compileKotlinTaskName)
            println(compilations.getByName("test").defaultSourceSetName)
        }
    }
}
