package com.cognifide.gradle.htl.tasks

import com.cognifide.gradle.htl.compiler.ScriptCompiler
import com.cognifide.gradle.htl.HtlException
import org.apache.sling.scripting.sightly.compiler.CompilationResult
import org.apache.sling.scripting.sightly.compiler.CompilerMessage
import org.gradle.api.tasks.TaskAction
import java.io.File

open class HtlValidate : HtlTask() {

    @TaskAction
    fun validate() {
//        val startTime = System.currentTimeMillis()
//        val results = ScriptCompiler(File(options.directory)).compileHTLScripts(htlFiles)
//        val endTime = System.currentTimeMillis()
//        printCompilationResults(results, endTime - startTime)
    }

  /*  private fun printCompilationResults(compilationResults: Map<File, CompilationResult>, time: Long) {
        var hasWarnings = false
        var hasErrors = false
        compilationResults.forEach { (script, result) ->
            if (result.warnings.isNotEmpty()) {
                result.warnings.forEach { message ->
                    project.logger.warn("w: ${format(script, message)}")
                }
                hasWarnings = true
            }
            if (result.errors.isNotEmpty()) {
                result.errors.forEach { message ->
                    project.logger.error("e: ${format(script, message)}")
                }
                hasErrors = true
            }
        }

        project.logger.lifecycle("Processed ${htlFiles.size} files in ${time}ms")

        if (options.failOnWarnings && hasWarnings) {
            throw HtlException("Compilation warnings were configured to fail the build.")
        }
        if (hasErrors) {
            throw HtlException("Please check the reported syntax errors.")
        }
    }

    private fun format(script: File, result: CompilerMessage) = "${script.path}: (${result.line}, ${result.column}): ${result.message.trim()}"

    init {
        description = "Validates HTL templates"
    }

   */

    companion object {
        const val NAME = "htlValidate"
    }
}
