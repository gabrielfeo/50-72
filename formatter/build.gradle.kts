plugins {
    id("multiplatform-native-library")
    id("multiplatform-js-browser-library")
}

group = "com.gabrielfeo"
version = "1.0-SNAPSHOT"

dependencies {
    commonTestImplementation(kotlin("test"))
}