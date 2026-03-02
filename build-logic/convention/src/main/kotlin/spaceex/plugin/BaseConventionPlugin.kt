package spaceex.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

open class BaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configurePlugin()
            configureAndroidPlatform()
            configureIOSPlatform()
            configureCommonDependencies()
            configureAndroidDependencies()
            configureIOSDependencies()
        }
    }

    open fun Project.configurePlugin() = Unit

    open fun Project.configureAndroidPlatform() = Unit

    open fun Project.configureIOSPlatform() = Unit

    open fun Project.configureCommonDependencies() = Unit

    open fun Project.configureAndroidDependencies() = Unit

    open fun Project.configureIOSDependencies() = Unit
}
