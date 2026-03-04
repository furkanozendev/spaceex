package spaceex.plugin

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import spaceex.ext.libs

class QualityConventionPlugin : BaseConventionPlugin() {
    override fun Project.configurePlugin() =
        with(project.pluginManager) {
            apply(project.libs.findPlugin("detekt").get().get().pluginId)
        }

    override fun Project.configureCommonDependencies() {
        dependencies.add(
            "detektPlugins",
            project.libs.findLibrary("detekt-formatting").get(),
        )

        extensions.configure(DetektExtension::class.java) {
            config.setFrom(files("$rootDir/detekt/detekt.yml"))
            buildUponDefaultConfig = true
            parallel = true

            source.setFrom(
                files(
                    "src/commonMain/kotlin",
                    "src/androidMain/kotlin",
                    "src/jvmMain/kotlin",
                    "src/iosMain/kotlin"
                )
            )
        }

        tasks.withType(io.gitlab.arturbosch.detekt.Detekt::class.java).configureEach {
            jvmTarget = "17"
            autoCorrect = true
            reports {
                html.required.set(true)
                xml.required.set(false)
                txt.required.set(false)
            }
        }
    }
}

