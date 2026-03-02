plugins {
    id("spaceex.plugin.library")
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.buildconfig)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:domain"))

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
    }
}
