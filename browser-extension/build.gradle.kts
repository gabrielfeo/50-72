plugins {
    id("base")
}

val distributionDir = "${buildDir.path}/distributions"

val descriptionFormatter = project(":browser-extension:description-formatter")
val contextMenuSetup = project(":browser-extension:context-menu-setup")

val aggregateDistributionsWithManifest by tasks.registering(Copy::class) {
    val descriptionFormatterDistribution = descriptionFormatter.tasks.named("jsBrowserDistribution")
    val contextMenuSetupDistribution = contextMenuSetup.tasks.named("jsBrowserDistribution")
    val descriptionFormatterOutputs = descriptionFormatterDistribution.map { it.outputs.files }
    val contextMenuSetupOutputs = contextMenuSetupDistribution.map { it.outputs.files }

    dependsOn(descriptionFormatterOutputs)
    dependsOn(contextMenuSetupOutputs)

    into(distributionDir)
    from(descriptionFormatterOutputs, contextMenuSetupOutputs)
    from(projectDir) {
        include("manifest.json")
    }
}

tasks.named("assemble") {
    dependsOn(aggregateDistributionsWithManifest)
}

tasks.named("check") {
    dependsOn(descriptionFormatter.tasks.named("check"))
    dependsOn(contextMenuSetup.tasks.named("check"))
}
