package com.cognifide.gradle.htl

import org.gradle.api.Project

open class HtlExtension(private val project: Project) {

    var extensionList = listOf(".htl")

    fun extensions(vararg ext: String) {
        extensionList = ext.toList()
    }

    companion object {
        const val NAME = "htl"
    }
}
