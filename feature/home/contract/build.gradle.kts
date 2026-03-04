plugins {
    id("spaceex.plugin.library")
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            implementation(project(":core:navigation"))
            implementation(libs.kotlinx.serialization.json)
        }
    }
}