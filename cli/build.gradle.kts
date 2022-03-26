plugins {
    id("multiplatform-native-app")
}

dependencies {
    commonMainImplementation(project(":formatter"))
    commonMainImplementation("com.github.ajalt.clikt:clikt:3.4.0")
    commonMainImplementation("com.squareup.okio:okio:3.0.0")
    commonTestImplementation("com.squareup.okio:okio-fakefilesystem:3.0.0")
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
