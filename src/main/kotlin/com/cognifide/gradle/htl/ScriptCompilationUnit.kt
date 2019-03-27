/*
 * Gradle HTL Plugin
 *
 * Copyright (C) 2019 Cognifide Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

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