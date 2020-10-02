package com.cognifide.gradle.htl.tasks

import org.gradle.api.tasks.TaskAction

open class HtlCompile : HtlTask() {

    @TaskAction
    fun compile() {
        // TODO
    }

    init {
        description = "Compiles HTL templates"
    }

    companion object {
        const val NAME = "htlCompile"
    }
}
