package com.cognifide.gradle.htl.test

import org.gradle.testkit.runner.GradleRunner
import java.io.File

open class BuildTest {

    fun prepareProject(path: String, definition: File.() -> Unit) = File("build/functionalTest/$path").apply {
        deleteRecursively()
        mkdirs()
        definition()
    }

    fun File.file(path: String, text: String) {
        resolve(path).apply { parentFile.mkdirs() }.writeText(text.trimIndent())
    }

    fun File.buildSrc(text: String) = file("buildSrc/build.gradle.kts", text)

    fun File.settingsGradle(text: String) = file("settings.gradle.kts", text)

    fun File.buildGradle(text: String) = file("build.gradle.kts", text)

    fun File.gradleProperties(text: String) = file("gradle.properties", text)

    fun runBuild(projectDir: File, vararg arguments: String, asserter: BuildResult.() -> Unit) {
        runBuild(projectDir, { withArguments(*arguments, "-i", "-S") }, asserter)
    }

    fun runBuild(projectDir: File, runnerOptions: GradleRunner.() -> Unit, asserter: BuildResult.() -> Unit) {
        BuildResult(runBuild(projectDir, runnerOptions), projectDir).apply(asserter)
    }

    fun runBuild(projectDir: File, options: GradleRunner.() -> Unit) = GradleRunner.create().run {
        forwardOutput()
        withPluginClasspath()
        withProjectDir(projectDir)
        apply(options)
        build()
    }

    fun rootPath(rootPath: String, path: String) = rootPath.takeIf { it.isNotBlank() }?.let { "$it/$path" } ?: path
}
