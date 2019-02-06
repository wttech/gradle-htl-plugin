package com.cognifide.gradle.htl.tasks

import com.cognifide.gradle.htl.HtlExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

open class Htl(project: Project) : DefaultTask() {

    init {
        description = ""
    }

    @Internal
    protected val ext = project.extensions.getByType(HtlExtension::class.java)

    @TaskAction
    fun htl() {
        project.logger.lifecycle("Hello World!")
    }

    companion object {
        const val NAME = "htl"
    }

}
