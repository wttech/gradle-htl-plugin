package com.cognifide.gradle.htl
import com.cognifide.gradle.htl.test.BuildTest
import org.junit.jupiter.api.Test

class HtlPluginTest : BuildTest() {

    @Test
    fun `should apply plugin correctly`() {
        val projectDir = prepareProject("minimal") {
            settingsGradle("")

            buildGradle("""
                plugins {
                    id("com.cognifide.htl")
                }
                
                htl {
                    // anything
                }
                """)
        }

        runBuild(projectDir, "tasks") {
            assertTask(":tasks")
        }
    }
}
