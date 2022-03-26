plugins {
    kotlin("multiplatform")
}

configureJsTarget {
    browser()
    binaries.executable()
}