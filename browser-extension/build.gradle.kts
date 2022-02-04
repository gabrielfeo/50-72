plugins {
    id("aggregator")
}

val aggregateDistributionsWithManifest by tasks.registering(aggregate.Aggregate::class) {
    destinationDir = file("$buildDir/distributions")
    val childrenDistributionTasks = subprojects.map { it.tasks.named("jsBrowserDistribution") }
    includeOutputsOf(childrenDistributionTasks)
    includePath("manifest.json")
}

val jsBrowserDistribution by tasks.registering {
    dependsOn(aggregateDistributionsWithManifest)
}

tasks.named("check") {
    val childrenCheckTasks = subprojects.map { it.tasks.named("check") }
    dependsOn(childrenCheckTasks)
}
