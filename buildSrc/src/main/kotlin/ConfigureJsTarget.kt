import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

fun Project.configureJsTarget(
    configure: KotlinJsTargetDsl.() -> Unit
) {
    configure<KotlinMultiplatformExtension> {
        js(LEGACY, configure)
    }
}