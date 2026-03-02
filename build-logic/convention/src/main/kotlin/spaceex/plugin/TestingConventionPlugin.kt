package spaceex.plugin

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.AppExtension
import spaceex.ext.libs
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class TestingConventionPlugin : BaseConventionPlugin() {
    override fun Project.configurePlugin() =
        with(project.pluginManager) {
            apply(project.libs.findPlugin("kotlinMultiplatform").get().get().pluginId)

            tasks.withType(Test::class.java).configureEach {
                useJUnitPlatform()

                try {
                    @Suppress("UNCHECKED_CAST")
                    val prop =
                        javaClass
                            .getMethod("getFailOnNoDiscoveredTests")
                            .invoke(this) as org.gradle.api.provider.Property<Boolean>
                    prop.set(false)
                } catch (_: Throwable) {
                    filter { isFailOnNoMatchingTests = false }
                }

                testLogging {
                    events("passed", "skipped", "failed")
                    showStandardStreams = false
                    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                }
            }
            // Aggregate target so CI/devs can run one task universally
            tasks.register("testAll").configure { dependsOn("check") }
        }

    override fun Project.configureAndroidPlatform() {
        extensions.findByType(AppExtension::class)?.apply {
            testOptions.unitTests.isIncludeAndroidResources = true
        }
        extensions.findByType(LibraryExtension::class)?.apply {
            testOptions.unitTests.isIncludeAndroidResources = true
        }
    }

    override fun Project.configureCommonDependencies() {
        extensions.findByType(KotlinMultiplatformExtension::class)?.apply {
            sourceSets.named("commonTest").configure {
                dependencies {
                    implementation(libs.findLibrary("kotlin-test").get())
                    implementation(libs.findLibrary("kotlin-test-annotations").get())
                    implementation(libs.findLibrary("coroutines-test").get())
                    implementation(libs.findLibrary("turbine").get())
                }
            }
        }
    }

    override fun Project.configureAndroidDependencies() {
        extensions.findByType(KotlinMultiplatformExtension::class)?.apply {
            sourceSets.findByName("androidUnitTest")?.let {
                sourceSets.named("androidUnitTest").configure {
                    dependencies {
                        implementation(libs.findLibrary("junit-jupiter").get())
                        implementation(libs.findLibrary("robolectric").get())
                    }
                }
            }
        }
    }
}
