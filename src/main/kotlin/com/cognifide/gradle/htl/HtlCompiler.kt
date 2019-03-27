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

import org.apache.sling.scripting.sightly.compiler.CompilationResult
import java.io.File
import java.util.LinkedHashMap
import org.apache.sling.scripting.sightly.compiler.SightlyCompiler

open class HtlCompiler
constructor(private val sourceDirectory: File) {

    private val compiler = SightlyCompiler()

    fun compileHTLScripts(scripts: List<File>) : Map<File, CompilationResult> {
        val compilationResult = LinkedHashMap<File, CompilationResult>(scripts.size)
        for (script in scripts) {
            val scriptCompilationUnit = ScriptCompilationUnit(sourceDirectory, script)
            compilationResult[script] = compiler.compile(scriptCompilationUnit)
            scriptCompilationUnit.dispose()
        }
        return compilationResult
    }
}