package spaceex.plugin

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import spaceex.ext.libs

class ApplicationConventionPlugin : BaseConventionPlugin() {
    override fun Project.configurePlugin() =
        with(project.pluginManager) {
            apply("spaceex.plugin.quality")

            apply(project.libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(project.libs.findPlugin("androidApplication").get().get().pluginId)
            apply(project.libs.findPlugin("composeMultiplatform").get().get().pluginId)
            apply(project.libs.findPlugin("composeCompiler").get().get().pluginId)
        }

    override fun Project.configureAndroidPlatform() {
        val minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
        val targetSdk = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()
        val versionCode = libs.findVersion("version-code").get().requiredVersion.toInt()
        val versionName = libs.findVersion("version-name").get().requiredVersion

        extensions.getByType<KotlinMultiplatformExtension>().apply {
            androidTarget()
        }

        extensions.getByType<ApplicationExtension>().apply {
            namespace = "com.spaceex"
            compileSdk = targetSdk
            defaultConfig {
                this.applicationId = "com.spaceex"
                this.minSdk = minSdk
                this.targetSdk = targetSdk
                this.versionCode = versionCode
                this.versionName = versionName
                this.applicationId = applicationId

                this.resValue("string", "app_name", "SpaceeX")
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
            }
            sourceSets["main"].apply {
                manifest.srcFile("src/androidMain/AndroidManifest.xml")
                res.srcDirs("src/androidMain/res")
                resources.srcDirs("src/commonMain/resources")
            }

            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                }
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }

    override fun Project.configureIOSPlatform() {
        extensions.getByType<KotlinMultiplatformExtension>().apply {
            listOf(
                iosX64(),
                iosArm64(),
                iosSimulatorArm64(),
            ).forEach { iosTarget ->
                iosTarget.binaries.framework {
                    baseName = "ComposeApp"
                    isStatic = false
                }
            }
        }
    }

    override fun Project.configureCommonDependencies() {
        val composeDependencies = extensions.getByType<ComposeExtension>().dependencies
        extensions.getByType<KotlinMultiplatformExtension>().apply {
            sourceSets.apply {
                commonMain {
                    compilerOptions {
                        freeCompilerArgs.add("-Xcontext-parameters")
                    }
                }
                commonMain.dependencies {
                    // Compose UI
                    implementation(composeDependencies.runtime)
                    implementation(composeDependencies.ui)
                    implementation(composeDependencies.foundation)
                    implementation(composeDependencies.material3)
                    implementation(composeDependencies.materialIconsExtended)
                    implementation(composeDependencies.components.uiToolingPreview)

                    implementation(libs.findLibrary("kotlinx-coroutines-core").get())

                    implementation(project.dependencies.platform(libs.findLibrary("koin-bom").get()))
                    implementation(libs.findLibrary("koin-core").get())
                    implementation(libs.findLibrary("koin-compose").get())

                    implementation(project(":core:navigation"))
                    implementation(project(":core:domain"))
                    implementation(project(":core:network"))
                }

                // Android specific dependencies
                getByName("androidMain").dependencies {
                    // Needed for ComponentActivity.setContent { } from androidx.activity.compose
                    implementation(libs.findLibrary("androidx-activity-compose").get())
                    // For enableEdgeToEdge and other KTX conveniences
                    implementation(libs.findLibrary("androidx-activity-ktx").get())
                }
            }
        }
    }
}
