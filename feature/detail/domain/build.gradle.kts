plugins {
    id("spaceex.plugin.domain")
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            implementation(project(":core:domain"))

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
    }
}
