import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

fun Project.configureNativeTarget(
    configure: KotlinNativeTargetWithHostTests.() -> Unit
) {
    configure<KotlinMultiplatformExtension> {
        val hostOs = System.getProperty("os.name")
        val nativeTarget = when {
            hostOs == "Mac OS X" -> macosX64("native")
            hostOs == "Linux" -> linuxX64("native")
            hostOs.startsWith("Windows") -> mingwX64("native")
            else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
        }
        configure(nativeTarget)
    }
}