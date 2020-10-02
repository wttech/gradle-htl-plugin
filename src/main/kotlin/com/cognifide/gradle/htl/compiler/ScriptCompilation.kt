package com.cognifide.gradle.htl.compiler

import org.apache.sling.scripting.sightly.compiler.CompilationUnit
import org.apache.sling.scripting.sightly.compiler.SightlyCompiler
import java.io.*

class ScriptCompilation(
        val script: File,
        private val sourceDir: File,
        private val compiler: SightlyCompiler
) : CompilationUnit {

    override fun getScriptName() = script.absolutePath.substring(sourceDir.absolutePath.length)

    override fun getScriptReader() = script.reader().buffered()

    val result by lazy { compiler.compile(this) }
}