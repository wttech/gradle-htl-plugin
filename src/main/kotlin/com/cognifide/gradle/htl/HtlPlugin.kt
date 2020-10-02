package com.cognifide.gradle.htl

import com.cognifide.gradle.htl.tasks.HtlCompile
import com.cognifide.gradle.htl.tasks.HtlValidate
import org.gradle.api.Plugin
import org.gradle.api.Project

open class HtlPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            extensions.create(HtlExtension.NAME, HtlExtension::class.java)
            tasks.register(HtlCompile.NAME, HtlCompile::class.java)
            tasks.register(HtlValidate.NAME, HtlValidate::class.java)
        }
    }
}
