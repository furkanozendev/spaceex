plugins {
    id("spaceex.plugin.data")
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            implementation(project(":core:domain"))
            api(project(":core:network"))
            api(project(":core:cache"))

            implementation(libs.ktor.client.core)
        }
    }
}
