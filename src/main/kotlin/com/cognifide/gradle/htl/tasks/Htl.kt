package com.cognifide.gradle.htl.tasks

import com.cognifide.gradle.htl.HtlExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

open class Htl @Inject constructor(project: Project) : DefaultTask() {

    init {
        description = ""
    }

    @InputFiles
    val htlFiles = project.fileTree(".").filter { it.path.endsWith(".htl") || it.path.endsWith(".html") }

    @Internal
    protected val ext = project.extensions.getByType(HtlExtension::class.java)

    @TaskAction
    fun htl() {
        htlFiles.forEach {
            project.logger.lifecycle("${it.path}")
        }
    }

    companion object {
        const val NAME = "htl"
    }

}
