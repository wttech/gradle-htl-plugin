package com.cognifide.gradle.htl

import com.cognifide.gradle.htl.compiler.ScriptCompiler
import org.gradle.api.Project
import org.gradle.api.tasks.util.PatternFilterable
import java.io.File

open class HtlExtension(private val project: Project) {

    val sourceDir = project.objects.fileProperty().apply {
        convention(project.layout.projectDirectory.file("src/main/content/jcr_root"))
    }

    fun sourceDir(dir: String) {
        sourceDir.set(project.layout.projectDirectory.file(dir))
    }

    val sourceFiles get() = project.fileTree(sourceDir).matching(sourceFilter)

    private var sourceFilter: PatternFilterable.() -> Unit = {
        include("**/*.html", "**/*.htl")
    }

    fun sourceFilter(filter: PatternFilterable.() -> Unit) {
        this.sourceFilter = filter
    }

    val failOnWarnings = project.objects.property(Boolean::class.java).apply {
        convention(false)
    }

    fun failOnWarnings() {
        failOnWarnings.set(true)
    }

    // Reusable DSL

    val compiler get() = ScriptCompiler()

    fun compile(sourceDir: File, sourceFiles: Iterable<File>) = compiler.compile(sourceDir, sourceFiles)

    fun compile() = compile(sourceDir.get().asFile, sourceFiles)

    companion object {
        const val NAME = "htl"
    }
}
