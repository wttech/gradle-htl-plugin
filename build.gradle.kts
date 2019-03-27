import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

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

plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm")
    id("com.jfrog.bintray")
    id("maven-publish")
}

group = "com.cognifide.gradle"
version = "0.0.1"
description = "Gradle HTL Plugin"
defaultTasks = listOf("build", "publishToMavenLocal")

repositories {
    mavenLocal()
    jcenter()
}

dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.21")
    implementation("commons-io:commons-io:2.6")
    implementation("org.apache.sling:org.apache.sling.scripting.sightly.runtime:1.1.0-1.4.0")
    implementation("org.apache.sling:org.apache.sling.scripting.sightly.compiler:1.1.0-1.4.0")
    implementation("org.apache.sling:org.apache.sling.scripting.sightly.compiler.java:1.1.0-1.4.0")
    implementation("org.apache.commons:commons-lang3:3.5")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation(gradleTestKit())
    testImplementation("org.skyscreamer:jsonassert:1.5.0")
    testImplementation("org.junit-pioneer:junit-pioneer:0.2.2")
}

gradlePlugin {
    plugins {
        create("htl") {
            id = "com.cognifide.gradle.htl"
            implementationClass = "com.cognifide.gradle.htl.HtlPlugin"
        }
    }
}
tasks {
    register<Jar>("sourcesJar") {
        classifier = "sources"
        dependsOn("classes")
        from(sourceSets["main"].allSource)
    }

    named<Task>("build") {
        dependsOn("sourcesJar")
    }

    named<Task>("publishToMavenLocal") {
        dependsOn("sourcesJar")
    }

    named<ProcessResources>("processResources") {
        dependsOn("buildJson", "tailerZip")
    }

    named<Test>("test") {
        useJUnitPlatform()
        dependsOn(named("publishToMavenLocal"))
    }
}

apply(from = "gradle/publish.gradle.kts")