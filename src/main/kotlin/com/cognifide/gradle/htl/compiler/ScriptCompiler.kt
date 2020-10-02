package com.cognifide.gradle.htl.compiler

import org.apache.sling.scripting.sightly.compiler.SightlyCompiler
import java.io.File

open class ScriptCompiler {

    private val compiler = SightlyCompiler()

    fun compile(sourceDir: File, scripts: Iterable<File>) = scripts.asSequence().map { ScriptCompilation(it, sourceDir, compiler) }
}
