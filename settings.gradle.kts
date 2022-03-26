rootProject.name = "50-72"

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

includeBuild("build-src")
include(
    ":formatter",
    ":cli",
    ":browser-extension",
    ":browser-extension:context-menu-setup",
    ":browser-extension:description-formatter"
)
