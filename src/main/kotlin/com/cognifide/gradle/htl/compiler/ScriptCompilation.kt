package com.cognifide.gradle.htl.compiler

import org.apache.sling.scripting.sightly.compiler.CompilationUnit
import org.apache.sling.scripting.sightly.compiler.SightlyCompiler
import java.io.File

class ScriptCompilation(
    val script: File,
    private val sourceDir: File,
    private val compiler: SightlyCompiler
) : CompilationUnit {

    override fun getScriptName() = script.absolutePath.substring(sourceDir.absolutePath.length)

    private val reader by lazy { script.reader().buffered(BUFFER_SIZE) }

    override fun getScriptReader() = reader

    val result by lazy { compiler.compile(this).also { reader.close() } }

    companion object {
        const val BUFFER_SIZE = 16 * 1024
    }
}
