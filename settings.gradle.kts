rootProject.name = "50-72"

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(
    ":formatter",
    ":cli"
)
