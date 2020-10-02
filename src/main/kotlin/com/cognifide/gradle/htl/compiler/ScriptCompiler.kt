package com.cognifide.gradle.htl.compiler

import org.apache.sling.scripting.sightly.compiler.CompilationResult
import org.apache.sling.scripting.sightly.compiler.SightlyCompiler
import java.io.File
import java.util.LinkedHashMap

open class ScriptCompiler(private val sourceDirectory: File) {

    private val compiler = SightlyCompiler()

    fun compileHTLScripts(scripts: List<File>) : Map<File, CompilationResult> {
        val compilationResult = LinkedHashMap<File, CompilationResult>(scripts.size)
        for (script in scripts) {
            val scriptCompilationUnit = ScriptCompileUnit(sourceDirectory, script)
            compilationResult[script] = compiler.compile(scriptCompilationUnit)
            scriptCompilationUnit.dispose()
        }
        return compilationResult
    }
}