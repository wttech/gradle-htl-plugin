package com.cognifide.gradle.htl.test

import org.apache.commons.io.FilenameUtils
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.*
import java.io.File

class BuildResult(val result: BuildResult, val projectDir: File) {

    fun file(path: String): File = projectDir.resolve(path)

    fun files(path: String, pattern: String) = projectDir.resolve(path)
            .walk().filter { FilenameUtils.wildcardMatch(it.absolutePath, pattern) }.toList()

    fun assertFileExists(path: String) = assertFileExists(file(path))

    fun assertFileNotExists(path: String) = assertFileNotExists(file(path))

    fun assertFileExists(file: File) {
        assertTrue({ file.exists() }, "File does not exist: $file")
    }

    fun assertFileNotExists(file: File) {
        assertFalse({ file.exists() }, "File exists: $file")
    }

    fun assertTask(taskPath: String, outcome: TaskOutcome = TaskOutcome.SUCCESS) {
        val task = result.task(taskPath)
        assertNotNull(task, "Build result does not contain task with path '$taskPath'")
        assertEquals(outcome, task?.outcome)
    }

    fun assertTasks(taskName: String, outcome: TaskOutcome = TaskOutcome.SUCCESS) {
        result.tasks.filter { it.path.endsWith(":$taskName") }.forEach { assertTask(it.path, outcome) }
    }

    private fun normalizeString(string: String) = string.trimIndent().replace("\r\n", "\n").trim()
}
