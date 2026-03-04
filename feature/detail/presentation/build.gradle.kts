plugins {
    id("spaceex.plugin.presentation")
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            implementation(project(":core:presentation"))
            implementation(project(":core:designsystem"))
            implementation(project(":feature:detail:contract"))
            implementation(project(":feature:home:contract"))
            implementation(project(":feature:detail:domain"))

            implementation(compose.components.resources)
        }
    }
}
