package com.cognifide.gradle.htl

import org.apache.sling.scripting.sightly.compiler.CompilationUnit
import java.io.*
import java.nio.charset.StandardCharsets

class ScriptCompilationUnit @Throws(FileNotFoundException::class)
constructor(private val sourceDirectory: File, private val script: File) : CompilationUnit {

    private var scriptReader: Reader
    private var scriptName: String? = null

    init {
        scriptReader = BufferedReader(InputStreamReader(FileInputStream(script), StandardCharsets.UTF_8), _16K)
    }

    override fun getScriptName(): String {
        if (scriptName == null) {
            scriptName = script.absolutePath.substring(sourceDirectory.absolutePath.length)
        }
        return scriptName as String
    }

    override fun getScriptReader(): Reader {
        return scriptReader
    }

    fun dispose() {
        scriptReader.close()
    }

    companion object {
        private val _16K = 16384
    }
}