plugins {
    id("spaceex.plugin.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kamel.image)
            implementation(libs.kamel.image.default)
        }
    }
}