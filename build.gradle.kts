import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("org.jetbrains.dokka") version "1.4.0-rc"
    id("com.gradle.plugin-publish") version "0.11.0"
    id("io.gitlab.arturbosch.detekt") version "1.7.0"
    id("com.jfrog.bintray") version "1.8.4"
    id("net.researchgate.release") version "2.8.1"
    id("com.github.breadmoirai.github-release") version "2.2.10"
}

group = "com.cognifide.gradle"
description = "Gradle HTL Plugin"
defaultTasks(":publishToMavenLocal")

val functionalTestSourceSet = sourceSets.create("functionalTest")
gradlePlugin.testSourceSets(functionalTestSourceSet)

configurations.getByName("functionalTestImplementation").apply {
    extendsFrom(configurations.getByName("testImplementation"))
}

repositories {
    mavenLocal()
    jcenter()
}

dependencies {
    implementation(gradleApi())

    implementation("org.apache.commons:commons-lang3:3.9")
    implementation("commons-io:commons-io:2.6")

    implementation("org.apache.sling:org.apache.sling.scripting.sightly.runtime:1.1.0-1.4.0")
    implementation("org.apache.sling:org.apache.sling.scripting.sightly.compiler:1.1.0-1.4.0")
    implementation("org.apache.sling:org.apache.sling.scripting.sightly.compiler.java:1.1.0-1.4.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.7.0")
}

gradlePlugin {
    plugins {
        create("htl") {
            id = "com.cognifide.htl"
            implementationClass = "com.cognifide.gradle.htl.HtlPlugin"
            displayName = "HTL Plugin"
            description = "Provides Sling HTL Scripting Engine support"
        }
    }
}

tasks {
    register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        dependsOn("classes")
        from(sourceSets["main"].allSource)
    }

    dokkaJavadoc {
        outputDirectory = "$buildDir/javadoc"
    }

    register<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaJavadoc")
        from("$buildDir/javadoc")
    }

    withType<JavaCompile>().configureEach{
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
    }

    withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs = freeCompilerArgs + "-Xuse-experimental=kotlin.Experimental"
        }
    }

    withType<Detekt>().configureEach {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    build {
        dependsOn("sourcesJar", "javadocJar")
    }

    publishToMavenLocal {
        dependsOn(jar)
    }

    withType<Test>().configureEach {
        testLogging.showStandardStreams = true
        useJUnitPlatform()
    }

    test {
        dependsOn("detektTest")
    }

    register<Test>("functionalTest") {
        testClassesDirs = functionalTestSourceSet.output.classesDirs
        classpath = functionalTestSourceSet.runtimeClasspath

        useJUnitPlatform()
        mustRunAfter("test")
        dependsOn("jar", "detektFunctionalTest")
        outputs.dir("build/functionalTest")
    }

    afterReleaseBuild {
        dependsOn("bintrayUpload", "publishPlugins")
    }

    named("githubRelease") {
        mustRunAfter("release")
    }

    register("fullRelease") {
        dependsOn("release", "githubRelease")
    }
}

detekt {
    config.from(file("detekt.yml"))
    parallel = true
    autoCorrect = true
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}

pluginBundle {
    website = "https://github.com/wttech/gradle-htl-plugin"
    vcsUrl = "https://github.com/wttech/gradle-htl-plugin.git"
    description = "Gradle HTL Plugin"
    tags = listOf("aem", "sling", "htl", "html", "sightly")
}

bintray {
    user = (findProperty("bintray.user") ?: System.getenv("BINTRAY_USER"))?.toString()
    key = (findProperty("bintray.key") ?: System.getenv("BINTRAY_KEY"))?.toString()
    setPublications("mavenJava")
    with(pkg) {
        repo = "maven-public"
        name = "gradle-htl-plugin"
        userOrg = "cognifide"
        setLicenses("Apache-2.0")
        vcsUrl = "https://github.com/wttech/gradle-htl-plugin.git"
        setLabels("aem", "sling", "htl", "html", "sightly")
        with(version) {
            name = project.version.toString()
            desc = "${project.description} ${project.version}"
            vcsTag = project.version.toString()
        }
    }
    publish = (project.findProperty("bintray.publish") ?: "true").toString().toBoolean()
    override = (project.findProperty("bintray.override") ?: "false").toString().toBoolean()
}

githubRelease {
    owner("wttech")
    repo("gradle-htl-plugin")
    token((findProperty("github.token") ?: "").toString())
    tagName(project.version.toString())
    releaseName(project.version.toString())
    draft((findProperty("github.draft") ?: "false").toString().toBoolean())
    prerelease((findProperty("github.prerelease") ?: "false").toString().toBoolean())
    overwrite((findProperty("github.override") ?: "true").toString().toBoolean())
    gradle.projectsEvaluated { releaseAssets(listOf("jar", "sourcesJar", "javadocJar").map { tasks.named(it) }) }

    body { """
    |# What's new
    |
    |TBD
    |
    |# Upgrade notes
    |
    |Nothing to do.
    |
    |# Contributions
    |
    |None.
    """.trimMargin()
    }
}