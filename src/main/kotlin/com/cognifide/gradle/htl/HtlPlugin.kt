package com.cognifide.gradle.htl

import com.cognifide.gradle.htl.tasks.HtlValidate
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.language.base.plugins.LifecycleBasePlugin

open class HtlPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            plugins.apply(LifecycleBasePlugin::class.java)
            extensions.add(HtlExtension.NAME, HtlExtension(project))
            tasks.apply {
                val validate = register(HtlValidate.NAME, HtlValidate::class.java)
                named(LifecycleBasePlugin.CHECK_TASK_NAME) { it.dependsOn(validate) }
            }
        }
    }
}
