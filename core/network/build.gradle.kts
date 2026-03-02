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

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.logging)
            implementation(libs.kotlinx.serialization.json)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

buildConfig {
    packageName("com.spaceex.core.network.config")

    useKotlinOutput {
        internalVisibility = false
    }

    val baseUrl =
        project.findProperty("BASE_URL")?.toString()
            ?: System.getenv("BASE_URL")
            ?: error("BASE_URL is not set")

    buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
}
