package com.cognifide.gradle.htl

import com.cognifide.gradle.htl.tasks.Htl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.language.base.plugins.LifecycleBasePlugin

/**
 * Allows to access encrypted properties.
 *
 * Dedicated to be used only in subprojects (to avoid redefining 'fork' and 'props' tasks).
 *
 * For root project, instead apply plugin 'com.neva.fork'.
 */
open class HtlPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            extensions.create(HtlExtension.NAME, HtlExtension::class.java)
            val htl = tasks.register(Htl.NAME, Htl::class.java).get()
            tasks.findByName(LifecycleBasePlugin.ASSEMBLE_TASK_NAME)?.finalizedBy(htl)
        }
    }

}
