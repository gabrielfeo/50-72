/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

plugins {
    id("multiplatform-js-browser-extension")
}

val aggregateDistributionsWithManifest by tasks.registering(aggregate.Aggregate::class) {
    destinationDir.set(file("$buildDir/distributions"))
    val childrenDistributionTasks = subprojects.map { it.tasks.named("jsBrowserDistribution") }
    files.from("manifest.json", childrenDistributionTasks)
}

val jsBrowserDistribution by tasks.registering {
    dependsOn(aggregateDistributionsWithManifest)
}

tasks.named("check") {
    val childrenCheckTasks = subprojects.map { it.tasks.named("check") }
    dependsOn(childrenCheckTasks)
}
