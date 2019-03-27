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

import com.cognifide.gradle.htl.tasks.Htl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.language.base.plugins.LifecycleBasePlugin

/**
 * Allows to access encrypted properties.
 *
 * Dedicated to be used only in subprojects (to avoid redefining 'fork' and 'props' tasks).
 *
 * For root project, instead apply plugin 'com.neva.fork'.
 */
open class HtlPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            extensions.create(HtlExtension.NAME, HtlExtension::class.java)
            tasks.register(Htl.NAME, Htl::class.java)
            tasks.findByName(LifecycleBasePlugin.ASSEMBLE_TASK_NAME)?.finalizedBy(Htl.NAME)
        }
    }

}
