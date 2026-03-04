plugins {
    id("spaceex.plugin.library")
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            api(libs.navigation.compose)
        }
    }
}