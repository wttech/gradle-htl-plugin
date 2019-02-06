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

    @Input
    val extensions = options.extensionList

    @SkipWhenEmpty
    @InputFiles
    val htlFiles = project.fileTree(".").filter { file -> extensions.any { ext -> file.path.endsWith(ext) } }


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
