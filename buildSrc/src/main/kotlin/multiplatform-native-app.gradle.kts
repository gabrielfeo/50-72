plugins {
    kotlin("multiplatform")
}

configureNativeTarget {
    binaries {
        executable {
            entryPoint = "main"
        }
    }
}
