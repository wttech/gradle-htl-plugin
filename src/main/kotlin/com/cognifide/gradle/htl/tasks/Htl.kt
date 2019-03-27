/*
 * Gradle HTL Plugin
 *
 * Copyright (C) 2019 Cognifide Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.cognifide.gradle.htl.tasks

import com.cognifide.gradle.htl.HtlCompiler
import com.cognifide.gradle.htl.HtlExtension
import com.cognifide.gradle.htl.HtlValidationException
import org.apache.sling.scripting.sightly.compiler.CompilationResult
import org.apache.sling.scripting.sightly.compiler.CompilerMessage
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction
import java.io.File


open class Htl : DefaultTask() {

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
            throw HtlValidationException("Compilation warnings were configured to fail the build.")
        }
        if (hasErrors) {
            throw HtlValidationException("Please check the reported syntax errors.")
        }
    }

    private fun format(script: File, result: CompilerMessage) = "${script.path}: (${result.line}, ${result.column}): ${result.message.trim()}"

    companion object {
        const val NAME = "htl"
    }
}
