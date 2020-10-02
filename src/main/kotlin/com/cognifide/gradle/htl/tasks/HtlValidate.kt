package com.cognifide.gradle.htl.tasks

import com.cognifide.gradle.htl.HtlException
import org.apache.sling.scripting.sightly.compiler.CompilerMessage
import org.gradle.api.tasks.TaskAction
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

open class HtlValidate : HtlTask() {

    @OptIn(ExperimentalTime::class)
    @TaskAction
    fun validate() {
        val (compilations, duration) = measureTimedValue { htl.compile() }

        var hasWarnings = false
        var hasErrors = false
        var processed = 0

        compilations.forEach { c ->
            if (c.result.warnings.isNotEmpty()) {
                c.result.warnings.forEach { message ->
                    logger.warn("w: ${format(c.script, message)}")
                }
                hasWarnings = true
            }
            if (c.result.errors.isNotEmpty()) {
                c.result.errors.forEach { message ->
                    logger.error("e: ${format(c.script, message)}")
                }
                hasErrors = true
            }
            processed++
        }

        logger.lifecycle("Processed $processed file(s) in $duration")

        if (htl.failOnWarnings.get() && hasWarnings) {
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

    companion object {
        const val NAME = "htlValidate"
    }
}
