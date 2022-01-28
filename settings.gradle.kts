rootProject.name = "50-72"

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(
    ":formatter",
    ":cli",
    ":browser-extension",
    ":browser-extension:context-menu-setup",
    ":browser-extension:description-formatter"
)
