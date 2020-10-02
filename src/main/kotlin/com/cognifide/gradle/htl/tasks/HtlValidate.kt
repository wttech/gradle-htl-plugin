package com.cognifide.gradle.htl.tasks

import com.cognifide.gradle.htl.HtlException
import org.apache.sling.scripting.sightly.compiler.CompilerMessage
import org.gradle.api.tasks.*
import java.io.File

open class HtlValidate : HtlTask() {

    @InputFiles
    val sourceDir = htl.sourceFiles

    @OutputFile
    val reportFile = project.objects.fileProperty().convention(project.layout.buildDirectory.file("$name/htl.log"))

    @Internal
    val printIssues = project.objects.property(Boolean::class.java).apply {
        convention(true)
        project.findProject("htl.printIssues")?.toString()?.toBoolean()?.let { set(it) }
    }

    fun printIssues() {
        printIssues.set(true)
    }

    @Input
    val failOnWarnings = project.objects.property(Boolean::class.java).apply {
        convention(false)
        project.findProject("htl.failOnWarnings")?.toString()?.toBoolean()?.let { set(it) }
    }

    fun failOnWarnings() {
        failOnWarnings.set(true)
    }

    @Suppress("ComplexMethod", "NestedBlockDepth")
    @TaskAction
    fun validate() {
        var warnings = 0
        var errors = 0
        var issues = 0

        val reportFile = reportFile.get().asFile.apply {
            parentFile.mkdirs()
            delete()

            printWriter().use { printer ->
                htl.compile().forEach { c ->
                    val hasErrors = c.result.errors.isNotEmpty()
                    if (hasErrors) {
                        c.result.errors.forEach { printer.println("error: ${format(c.script, it)}") }
                        errors++
                    }
                    val hasWarnings = c.result.warnings.isNotEmpty()
                    if (hasWarnings) {
                        c.result.warnings.forEach { printer.println("warn: ${format(c.script, it)}") }
                        warnings++
                    }
                    if (hasErrors || hasWarnings) {
                        issues++
                    }
                }
            }
        }

        if (errors > 0 || warnings > 0) {
            logger.lifecycle("HTL issues found in $issues template(s) under directory '${htl.sourceDir.get().asFile}'")
            if (printIssues.get()) {
                reportFile.forEachLine { println(it) }
            } else {
                logger.lifecycle("HTL issues saved to report file '$reportFile'")
            }
        }
        if (errors > 0) {
            throw HtlException("HTL errors found in $errors template(s) under directory '${htl.sourceDir.get().asFile}'!")
        }
        if (failOnWarnings.get() && warnings > 0) {
            throw HtlException("HTL warnings found in $warnings template(s) under directory '${htl.sourceDir.get().asFile}'!")
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
