package com.cognifide.gradle.htl.compiler

import org.apache.sling.scripting.sightly.compiler.CompilationUnit
import java.io.*
import java.nio.charset.StandardCharsets

class ScriptCompileUnit(private val sourceDir: File, private val script: File) : CompilationUnit {

    private var scriptReader: Reader

    private var scriptName: String? = null

    override fun getScriptName(): String {
        if (scriptName == null) {
            scriptName = script.absolutePath.substring(sourceDir.absolutePath.length)
        }
        return scriptName as String
    }

    override fun getScriptReader(): Reader {
        return scriptReader
    }

    fun dispose() {
        scriptReader.close()
    }

    init {
        scriptReader = BufferedReader(InputStreamReader(FileInputStream(script), StandardCharsets.UTF_8), SIZE_16K)
    }

    companion object {
        private val SIZE_16K = 16384
    }
}