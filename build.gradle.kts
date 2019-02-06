plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm") version "1.3.20"
    id("com.jfrog.bintray") version "1.8.4"
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.20")
    implementation("org.apache.sling:org.apache.sling.scripting.sightly.runtime:1.1.0-1.4.0")
    implementation("org.apache.sling:org.apache.sling.scripting.sightly.compiler:1.1.0-1.4.0")
    implementation("org.apache.sling:org.apache.sling.scripting.sightly.compiler.java:1.1.0-1.4.0")

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
