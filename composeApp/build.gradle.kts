plugins {
    id("spaceex.plugin.application")
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            implementation(projects.core.cache)
            implementation(projects.core.network)
            implementation(projects.core.data)
            implementation(projects.core.domain)
            implementation(projects.core.navigation)
            implementation(projects.core.designsystem)
            implementation(projects.feature.home.contract)
            implementation(projects.feature.home.data)
            implementation(projects.feature.home.domain)
            implementation(projects.feature.home.presentation)
            implementation(projects.feature.detail.data)
            implementation(projects.feature.detail.domain)
            implementation(projects.feature.detail.presentation)
            implementation(libs.ui.backhandler)
        }
    }
}
