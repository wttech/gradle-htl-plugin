package com.cognifide.gradle.htl

import org.apache.sling.scripting.sightly.compiler.CompilationResult
import java.io.File
import java.util.LinkedHashMap
import org.apache.sling.scripting.sightly.compiler.SightlyCompiler

open class HtlCompiler
constructor(private val sourceDirectory: File) {

    private val compiler = SightlyCompiler()

    fun compileHTLScripts(scripts: List<File>) : Map<File, CompilationResult> {
        val compilationResult = LinkedHashMap<File, CompilationResult>(scripts.size)
        for (script: File in scripts) {
            val scriptCompilationUnit = ScriptCompilationUnit(sourceDirectory, script)
            compilationResult[script] = compiler.compile(scriptCompilationUnit)
            scriptCompilationUnit.dispose()
        }
        return compilationResult
    }
}