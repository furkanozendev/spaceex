plugins {
    id("spaceex.plugin.data")
    alias(libs.plugins.buildconfig)
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            implementation(project(":core:domain"))
            implementation(project(":core:network"))
            implementation(project(":core:cache"))

            implementation(libs.ktor.client.core)
        }
    }
}

buildConfig {
    packageName("com.spaceex.core.data.config")

    useKotlinOutput {
        internalVisibility = false
    }
}
