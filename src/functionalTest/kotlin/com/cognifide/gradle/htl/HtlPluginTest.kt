package com.cognifide.gradle.htl
import com.cognifide.gradle.htl.test.BuildTest
import org.gradle.testkit.runner.TaskOutcome
import org.gradle.testkit.runner.UnexpectedBuildFailure
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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

    @Test
    fun `should pass validation`() {
        val projectDir = prepareProject("valid") {
            settingsGradle("")

            buildGradle("""
                plugins {
                    id("com.cognifide.htl")
                }
                """)

            file("src/main/content/jcr_root/apps/mysite/components/valid/valid.html", """
                <sly data-sly-use.model="com.cognifide.aem.training.core.breadcrumb.BreadcrumbModel">
                  <sly data-sly-list.parentBreadcrumpEntry="$\{model.parentsBreadcrumpEntries}">
                    <a href="$\{parentBreadcrumpEntry.path}" data-sly-test="$\{!parentBreadcrumpEntryList.last}">$\{parentBreadcrumpEntry.name}/</a>
                    <sly data-sly-test="$\{parentBreadcrumpEntryList.last}">$\{model.currentPageName}</sly>
                  </sly>
                </sly>
            """)
        }

        runBuild(projectDir, "htlValidate") {
            assertTask(":htlValidate")
        }
    }

    @Test
    fun `should not pass validation`() {
        val projectDir = prepareProject("invalid") {
            settingsGradle("")

            buildGradle("""
                plugins {
                    id("com.cognifide.htl")
                }
                
                htl {
                    failOnWarnings()
                }
                """)

            file("src/main/content/jcr_root/apps/mysite/components/invalid/invalid.html", """
                <sly data-sly-use123.model="com.cognifide.aem.training.core.breadcrumb.BreadcrumbModel">
                  <sly data-sly-list.parentBreadcrumpEntry="$\{model.parentsBreadcrumpEntries}">
                    <a href="$\{parentBreadcrumpEntry.path}" data-sly-test="$\{!parentBreadcrumpEntryList.last}">$\{parentBreadcrumpEntry.name}/</a>
                    <sly data-sly-test="$\{parentBreadcrumpEntryList.last}">$\{model.currentPageName}</sly>
                  </sly>
                </sly>
            """)
        }

        assertThrows<UnexpectedBuildFailure> {
            runBuild(projectDir, "htlValidate") {
                assertTask(":htlValidate", TaskOutcome.FAILED)
            }
        }
    }
}
