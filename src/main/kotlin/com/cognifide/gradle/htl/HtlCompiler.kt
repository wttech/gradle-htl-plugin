package com.cognifide.gradle.htl

import org.apache.sling.scripting.sightly.compiler.CompilationResult
import java.io.File

open class HtlCompiler {
    fun compileHTLScripts(scripts: List<File>) : Map<File, CompilationResult>? {
        return mapOf()
    }
}