package com.cognifide.gradle.htl.tasks

import com.cognifide.gradle.htl.HtlExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.*
import javax.inject.Inject

open class Htl @Inject constructor(project: Project) : DefaultTask() {

    init {
        description = ""
    }

    @Internal
    protected val options = project.extensions.getByType(HtlExtension::class.java)

    @Internal
    val extensions = options.extensions

    @SkipWhenEmpty
    @InputFiles
    val htlFiles = project.fileTree(options.directory).matching { it.include(extensions) }


    @TaskAction
    fun htl() {
        project.logger.lifecycle("Welcome to HTL validation!")
        htlFiles.forEach {
            project.logger.lifecycle("${it.path}")
        }
    }

    companion object {
        const val NAME = "htl"
    }

}
