/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */        

/*
* A project that bundles together a manifest.json and any number of JS executables
* to generate a browser extension.
*/

plugins {
    id("base")
}

tasks.named("assemble") {
    dependsOn(tasks.withType(aggregate.Aggregate::class))
}
