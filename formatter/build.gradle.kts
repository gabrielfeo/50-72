plugins {
    id("multiplatform-native-library")
}

group = "com.gabrielfeo"
version = "1.0-SNAPSHOT"

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
