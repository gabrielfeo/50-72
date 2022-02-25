plugins {
    id("multiplatform-native-app")
}

dependencies {
    commonMainImplementation(project(":formatter"))
    commonMainImplementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.4")
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
