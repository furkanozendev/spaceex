package spaceex.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import spaceex.ext.libs
import spaceex.ext.moduleName

/**
 * Convention plugin for domain layer modules.
 *
 * Domain layer is PURE - contains:
 * - Use cases / Interactors
 * - Repository interfaces
 * - Domain models / Entities
 * - Domain exceptions
 *
 * NO platform-specific code, NO UI, NO data source implementations.
 */
class DomainConventionPlugin : BaseConventionPlugin() {

    override fun Project.configurePlugin() =
        with(project.pluginManager) {
            apply("spaceex.plugin.quality")
            apply(project.libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(project.libs.findPlugin("androidLibrary").get().get().pluginId)
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
            // No buildConfig - domain shouldn't need it
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
        extensions.getByType<KotlinMultiplatformExtension>().apply {
            sourceSets.apply {
                commonMain {
                    compilerOptions {
                        freeCompilerArgs.add("-Xcontext-parameters")
                    }
                }
                commonMain.dependencies {
                    implementation(libs.findLibrary("kotlinx-coroutines-core").get())
                }
            }
        }
    }
}