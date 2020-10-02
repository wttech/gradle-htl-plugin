package com.cognifide.gradle.htl.tasks

import com.cognifide.gradle.htl.HtlExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal

open class HtlTask : DefaultTask() {

    @Internal
    protected val htl = project.extensions.getByType(HtlExtension::class.java)
}
