package com.cognifide.gradle.htl

import com.cognifide.gradle.htl.compiler.ScriptCompiler
import org.gradle.api.Project
import org.gradle.api.tasks.util.PatternFilterable
import java.io.File

open class HtlExtension(private val project: Project) {

    val sourceDir = project.objects.directoryProperty().apply {
        convention(project.layout.projectDirectory.dir("src/main/content/jcr_root"))
        project.findProject("htl.sourceDir")?.toString()?.let { path ->
            set(project.layout.projectDirectory.dir(path))
        }
    }

    fun sourceDir(dir: String) {
        sourceDir.set(project.layout.projectDirectory.dir(dir))
    }

    val sourceFiles get() = project.fileTree(sourceDir).matching(sourceFilter)

    private var sourceFilter: PatternFilterable.() -> Unit = {
        include("**/*.html", "**/*.htl")
    }

    fun sourceFilter(filter: PatternFilterable.() -> Unit) {
        this.sourceFilter = filter
    }

    // Reusable DSL

    val compiler get() = ScriptCompiler()

    fun compile(sourceDir: File, sourceFiles: Iterable<File>) = compiler.compile(sourceDir, sourceFiles)

    fun compile() = compile(sourceDir.get().asFile, sourceFiles)

    companion object {
        const val NAME = "htl"
    }
}
