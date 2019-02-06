package com.cognifide.gradle.htl.tasks

import com.cognifide.gradle.htl.HtlCompiler
import com.cognifide.gradle.htl.HtlExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.*
import java.io.File
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
    val htlFiles = project.fileTree(options.directory).matching { it.include(extensions) }.toList()


    @TaskAction
    fun htl() {
        project.logger.lifecycle("Welcome to HTL validation!")
        val results = HtlCompiler(File(options.directory)).compileHTLScripts(htlFiles)
        results.filterValues { it.errors.isNotEmpty() || it.warnings.isNotEmpty() }.forEach { file, result ->
            project.logger.lifecycle("${file.path}:")
            result.errors.forEach {
                project.logger.lifecycle("ERROR: ${it.message}")
            }
            result.warnings.forEach {
                project.logger.lifecycle("WARNING: ${it.message}")
            }
        }
    }

    companion object {
        const val NAME = "htl"
    }

}
