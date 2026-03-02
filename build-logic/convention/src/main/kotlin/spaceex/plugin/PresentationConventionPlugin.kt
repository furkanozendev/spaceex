package spaceex.plugin

import com.android.build.gradle.LibraryExtension
import spaceex.ext.libs
import spaceex.ext.moduleName
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Convention plugin for presentation layer modules.
 *
 * Presentation layer contains:
 * - Composable screens and components
 * - ViewModels
 * - UI state classes
 * - UI mappers (domain -> UI models)
 *
 * Depends on domain layer (NOT data - DI handles that).
 * Full Compose stack available.
 */
class PresentationConventionPlugin : BaseConventionPlugin() {

    override fun Project.configurePlugin() =
        with(project.pluginManager) {
            apply("spaceex.plugin.quality")
            apply(project.libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(project.libs.findPlugin("androidLibrary").get().get().pluginId)
            apply(project.libs.findPlugin("composeMultiplatform").get().get().pluginId)
            apply(project.libs.findPlugin("composeCompiler").get().get().pluginId)
        }

    override fun Project.configureAndroidPlatform() {
        val minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
        val targetSdk = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()

        extensions.getByType<KotlinMultiplatformExtension>().apply {
            androidTarget()
        }

        extensions.getByType<LibraryExtension>().apply {
            namespace = moduleName
            compileSdk = targetSdk
            defaultConfig {
                this.minSdk = minSdk
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
                    baseName = moduleName
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
                    implementation(composeDependencies.foundation)
                    implementation(composeDependencies.material3)
                    implementation(composeDependencies.materialIconsExtended)
                    implementation(composeDependencies.ui)
                    implementation(composeDependencies.components.uiToolingPreview)

                    // Coroutines
                    implementation(libs.findLibrary("kotlinx-coroutines-core").get())

                    // Koin DI
                    implementation(project.dependencies.platform(libs.findLibrary("koin-bom").get()))
                    implementation(libs.findLibrary("koin-core").get())
                    implementation(libs.findLibrary("koin-compose").get())
                    implementation(libs.findLibrary("koin-compose-viewmodel").get())

                    // ViewModel & Lifecycle
                    implementation(libs.findLibrary("androidx-lifecycle-viewmodelCompose").get())
                    implementation(libs.findLibrary("androidx-lifecycle-runtimeCompose").get())

                    // Core modules
                    /*implementation(project(":core:domain"))
                    implementation(project(":core:navigation"))*/
                }
            }
        }

        // Android Studio preview support
        dependencies.add("debugImplementation", composeDependencies.uiTooling)
    }
}