import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "spaceex.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.org.jetbrains.kotlin.multiplatform.gradle.plugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.compose.plugin)
    compileOnly(libs.kotlin.gradlePlugin)

    // Quality plugins need to be on classpath at runtime because we apply them programmatically
    implementation(libs.kotlin.serialization.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.ktlint.gradlePlugin)
    implementation(libs.kover.gradlePlugin)
}

gradlePlugin {
    plugins {
        // Feature module convention plugin
        register("spaceex.plugin.feature") {
            id = "spaceex.plugin.feature"
            implementationClass = "spaceex.plugin.FeatureConventionPlugin"
        }
        // Data module convention plugin
        register("spaceex.plugin.data") {
            id = "spaceex.plugin.data"
            implementationClass = "spaceex.plugin.DataConventionPlugin"
        }
        // Domain module convention plugin
        register("spaceex.plugin.domain") {
            id = "spaceex.plugin.domain"
            implementationClass = "spaceex.plugin.DomainConventionPlugin"
        }
        // Presentation module convention plugin
        register("spaceex.plugin.presentation") {
            id = "spaceex.plugin.presentation"
            implementationClass = "spaceex.plugin.PresentationConventionPlugin"
        }
        // Ui module convention plugin
        register("spaceex.plugin.ui") {
            id = "spaceex.plugin.ui"
            implementationClass = "spaceex.plugin.UiConventionPlugin"
        }
        // Application module convention plugin
        register("spaceex.plugin.application") {
            id = "spaceex.plugin.application"
            implementationClass = "spaceex.plugin.ApplicationConventionPlugin"
        }
        // Testing convention plugin
        register("spaceex.plugin.testing") {
            id = "spaceex.plugin.testing"
            implementationClass = "spaceex.plugin.TestingConventionPlugin"
        }
        // Static Code Analysis convention plugin
        register("spaceex.plugin.quality") {
            id = "spaceex.plugin.quality"
            implementationClass = "spaceex.plugin.QualityConventionPlugin"
        }
        // Library module convention plugin
        register("spaceex.plugin.library") {
            id = "spaceex.plugin.library"
            implementationClass = "spaceex.plugin.LibraryConventionPlugin"
        }
    }
}
