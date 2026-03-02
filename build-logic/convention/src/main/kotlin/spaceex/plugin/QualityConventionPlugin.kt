package spaceex.plugin

import spaceex.ext.libs
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

class QualityConventionPlugin : BaseConventionPlugin() {
    override fun Project.configurePlugin() =
        with(project.pluginManager) {
            apply(project.libs.findPlugin("detekt").get().get().pluginId)
            apply(project.libs.findPlugin("ktlint").get().get().pluginId)
            apply(project.libs.findPlugin("kover").get().get().pluginId)
        }

    override fun Project.configureCommonDependencies() {
        dependencies.add(
            "detektPlugins",
            project.libs.findLibrary("detekt-formatting").get(),
        )
        dependencies.ktlintRuleset(
            project.libs.findLibrary("ktlint-ruleset-compose").get(),
        )

        extensions.configure(io.gitlab.arturbosch.detekt.extensions.DetektExtension::class.java) {
            buildUponDefaultConfig = true
            parallel = true
            autoCorrect = true
        }

        extensions.configure(org.jlleitschuh.gradle.ktlint.KtlintExtension::class.java) {
            android.set(true)
            ignoreFailures.set(false)

            verbose.set(true)
            outputToConsole.set(true)
            coloredOutput.set(true)

            additionalEditorconfig.set(
                mapOf(
                    "ktlint_function_naming_ignore_when_annotated_with" to "Composable, Test",
                ),
            )

            filter {
                exclude("**/build/**")
                exclude("**/generated/**")
            }

            kotlinScriptAdditionalPaths {
                include(fileTree("scripts/"))
            }
        }

        // Kover: keep defaults, or configure if needed
        // extensions.configure(kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension::class.java) { }
    }
}

private fun DependencyHandler.ktlintRuleset(dep: Any) {
    add("ktlintRuleset", dep)
}
