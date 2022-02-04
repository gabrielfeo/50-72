plugins {
    id("aggregator")
}

val aggregateDistributionsWithManifest by tasks.registering(aggregate.Aggregate::class) {
    destinationDir.set(file("$buildDir/distributions"))
    val childrenDistributionTasks = subprojects.map { it.tasks.named("jsBrowserDistribution") }
    outputsOf(childrenDistributionTasks)
    path("manifest.json")
}

val jsBrowserDistribution by tasks.registering {
    dependsOn(aggregateDistributionsWithManifest)
}

tasks.named("check") {
    val childrenCheckTasks = subprojects.map { it.tasks.named("check") }
    dependsOn(childrenCheckTasks)
}
