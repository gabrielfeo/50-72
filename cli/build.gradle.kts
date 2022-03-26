plugins {
    id("multiplatform-native-app")
}

dependencies {
    commonMainImplementation(project(":formatter"))
    commonMainImplementation("com.github.ajalt.clikt:clikt:3.4.0")
    commonMainImplementation("com.squareup.okio:okio:3.0.0")
    commonTestImplementation(kotlin("test"))
    commonTestImplementation("com.squareup.okio:okio-fakefilesystem:3.0.0")
}
