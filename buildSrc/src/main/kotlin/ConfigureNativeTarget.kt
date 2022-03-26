import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

private data class SharedSourceSets(
    val main: KotlinSourceSet,
    val test: KotlinSourceSet
)

fun Project.configureNativeTargets(
    configure: KotlinNativeTargetWithHostTests.() -> Unit
) {
    configure<KotlinMultiplatformExtension> {
        val sharedSourceSets = createNativeSharedSourceSets(sourceSets)
        macosX64 {
            addSharedNativeSourceSets(sourceSets, sharedSourceSets)
            configure()
        }
        // linuxX64 {
        //     addSharedSourceSets(sourceSets)
        //     configure()
        // }
    }
}

private fun KotlinNativeTarget.addSharedNativeSourceSets(
    sourceSets: NamedDomainObjectContainer<KotlinSourceSet>,
    sharedSourceSets: SharedSourceSets,
) {
    sourceSets.getByName("${name}Main").dependsOn(sharedSourceSets.main)
    sourceSets.getByName("${name}Test").dependsOn(sharedSourceSets.test)
}

private fun createNativeSharedSourceSets(
    sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
): SharedSourceSets {
    val nativeMain by sourceSets.creating
    val nativeTest by sourceSets.creating
    val commonMain by sourceSets.getting
    val commonTest by sourceSets.getting
    nativeMain.dependsOn(commonMain)
    nativeTest.dependsOn(commonTest)
    return SharedSourceSets(nativeMain, nativeTest)
}
