plugins {
    id("multiplatform-js-browser-app")
}

dependencies {
    jsMainImplementation(project(":formatter"))
}
