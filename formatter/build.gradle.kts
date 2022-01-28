plugins {
    kotlin("multiplatform") version "1.6.10"
}

group = "com.gabrielfeo"
version = "1.0-SNAPSHOT"

kotlin {
    val hostOs = System.getProperty("os.name")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        hostOs.startsWith("Windows") -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.register("buildRelease", Copy::class) {
    dependsOn("${project.path}:linkReleaseExecutableNative")
    into("./build/bin/fullRelease")
    from("./build/bin/native/releaseExecutable") {
        include("*.kexe")
        rename {
            "50-72"
        }
    }
}

tasks.register("updateHooks", Copy::class) {
    dependsOn("buildRelease")
    into("$rootDir/.git/hooks")
    from("$rootDir/hooks")
}
