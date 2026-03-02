plugins {
    id("spaceex.plugin.data")
    alias(libs.plugins.buildconfig)
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            implementation(project(":core:domain"))

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
