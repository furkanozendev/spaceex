plugins {
    id("spaceex.plugin.application")
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            /*implementation(project(":feature:auth"))
            implementation(project(":feature:home"))*/
            implementation(libs.ui.backhandler)
        }
    }
}
