/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

plugins {
    id("multiplatform-js-browser-executable")
}

kotlin {
    jvm()
}

dependencies {
    commonMainImplementation(project(":formatter"))
    commonMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    commonTestImplementation(kotlin("test"))
}
