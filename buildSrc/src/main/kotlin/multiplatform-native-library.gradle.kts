plugins {
    kotlin("multiplatform")
}

configureNativeTarget {
    binaries.sharedLib()
}
