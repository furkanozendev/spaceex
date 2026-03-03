plugins {
    id("spaceex.plugin.application")
}

kotlin {
    sourceSets {
        val commonMain by getting
        commonMain.dependencies {
            /*implementation(project(":feature:home"))
            implementation(project(":feature:detail"))*/
            implementation(libs.ui.backhandler)
        }
    }
}
