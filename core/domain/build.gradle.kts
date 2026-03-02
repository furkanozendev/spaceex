plugins {
    id("spaceex.plugin.domain")
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            implementation(kotlin("stdlib"))
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
