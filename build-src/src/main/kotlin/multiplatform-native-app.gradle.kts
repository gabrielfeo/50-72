plugins {
    kotlin("multiplatform")
}

configureNativeTargets {
    binaries {
        executable {
            entryPoint = "main"
        }
    }
}
