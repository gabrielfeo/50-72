/*
 * Copyright (c) 2022 Gabriel Feo
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("unused")

import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.SelfResolvingDependency
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

fun KotlinMultiplatformExtension.targetsStructureToString(
    configurations: Set<Configuration>
) = buildString {
    fun indent(level: Int) = buildString {
        repeat(level * 4) {
            append(' ')
        }
    }
    fun KotlinSourceSet.appendRecursively(level: Int, default: KotlinSourceSet) {
        val prefix = if (this == default) ">" else ""
        appendLine("${indent(level)}${prefix}${name}")
        appendLine("${indent(level + 1)}Requires visibility of: $requiresVisibilityOf")
        appendLine("${indent(level + 1)}Configurations:")
        configurations
            .filter { it.name in relatedConfigurationNames }
            .associateWith { it.allDependencies }
            .forEach { (configuration, dependencies) ->
                appendLine(indent(level + 2) + "${configuration.name}:")
                val indent = indent(level + 3)
                val depStr = dependencies.joinToString("\n") {
                    indent + when (it) {
                        is SelfResolvingDependency -> it.resolve()
                        else -> it
                    }
                }
                appendLine(depStr)
            }
        dependsOn.forEach {
            it.appendRecursively(level + 1, default)
        }
    }
    targets.forEach { target ->
        appendLine("Target: ${target.name}")
        target.compilations.forEach { compilation ->
            appendLine("${indent(1)}Compilation: ${compilation.name} source sets:")
            compilation.kotlinSourceSets.forEach { sourceSet ->
                sourceSet.appendRecursively(level = 2, default = compilation.defaultSourceSet)
            }
        }
    }
}
