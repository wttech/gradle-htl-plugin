package com.cognifide.gradle.htl

open class HtlExtension {

    var extensions = listOf("**/*.html", "**/*.htl")
    var directory = "src/main/content/jcr_root/"

    fun extensions(vararg exts: String) {
        extensions = exts.toList()
    }

    fun directory(dir: String) {
        directory = dir
    }

    companion object {
        const val NAME = "htl"
    }
}
