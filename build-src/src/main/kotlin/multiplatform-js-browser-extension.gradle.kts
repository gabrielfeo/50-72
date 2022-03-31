/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering

/*
* A project that bundles together a manifest.json and any number of JS executables
* to generate a browser extension.
*/

plugins {
    id("base")
}

val aggregateSubprojectOutputs by tasks.registering(Copy::class) {
    destinationDir = file("$buildDir/outputs/extension")
    val subOutputs = project.subprojects.map {
        it.tasks.named("jsBrowserDistribution").map { task ->
            task.outputs
        }
    }
    from(subOutputs, "$projectDir/manifest.json")
}

tasks.named("assemble") {
    dependsOn(aggregateSubprojectOutputs)
}

tasks.named("check") {
    val subCheckTasks = subprojects.map {
        it.tasks.named("check")
    }
    dependsOn(subCheckTasks)
}
