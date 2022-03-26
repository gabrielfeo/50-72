plugins {
    kotlin("multiplatform")
}

configureNativeTargets {
    binaries.sharedLib()
}
