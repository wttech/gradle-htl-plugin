package com.cognifide.gradle.htl.tasks

import com.cognifide.gradle.htl.HtlExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.SkipWhenEmpty

open class HtlTask : DefaultTask() {

//    @Internal
//    protected val options = project.extensions.getByType(HtlExtension::class.java)
//
//    @Internal
//    val extensions = options.extensions
//
//    @SkipWhenEmpty
//    @InputFiles
//    val htlFiles = project.fileTree(options.directory).matching { it.include(extensions) }.toList()
}
