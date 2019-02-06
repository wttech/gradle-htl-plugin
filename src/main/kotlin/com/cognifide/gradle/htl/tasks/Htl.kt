package com.cognifide.gradle.htl.tasks

import com.cognifide.gradle.htl.HtlCompiler
import com.cognifide.gradle.htl.HtlExtension
import com.cognifide.gradle.htl.HtlValidationException
import org.apache.sling.scripting.sightly.compiler.CompilationResult
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
        val startTime = System.currentTimeMillis()
        val results = HtlCompiler(File(options.directory)).compileHTLScripts(htlFiles)
        val endTime = System.currentTimeMillis()
        printCompilationResults(results, endTime - startTime)
    }

    private fun printCompilationResults(compilationResults: Map<File, CompilationResult>, time: Long) {
        var hasWarnings = false
        var hasErrors = false
        compilationResults.forEach { script, result ->
            if (result.warnings.isNotEmpty()) {
                result.warnings.forEach { message ->
                    project.logger.warn("w: ${script.path}: (${message.line}, ${message.column}): ${message.message}")
                }
                hasWarnings = true
            }
            if (result.errors.isNotEmpty()) {
                result.errors.forEach { message ->
                    project.logger.error("e: ${script.path}: (${message.line}, ${message.column}): ${message.message}")
                }
                hasErrors = true
            }
        }

        project.logger.lifecycle("Processed ${htlFiles.size} files in ${time}ms")

        if (options.failOnWarnings && hasWarnings) {
            throw HtlValidationException("Compilation warnings were configured to fail the build.")
        }
        if (hasErrors) {
            throw HtlValidationException("Please check the reported syntax errors.")
        }
    }

    companion object {
        const val NAME = "htl"
    }
}
