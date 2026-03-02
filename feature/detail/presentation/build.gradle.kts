plugins {
    id("spaceex.plugin.presentation")
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            implementation(project(":core:presentation"))
            implementation(project(":feature:detail:domain"))
        }
    }
}
