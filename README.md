![Cognifide logo](https://assets.cognifide.com/github/cognifide-logo.png)

[![Gradle Status](https://gradleupdate.appspot.com/Cognifide/gradle-htl-plugin/status.svg?random=123)](https://gradleupdate.appspot.com/Cognifide/gradle-htl-plugin/status)
[![Apache License, Version 2.0, January 2004](docs/apache-license-badge.svg)](http://www.apache.org/licenses/)
![Travis Build](https://travis-ci.org/Cognifide/gradle-htl-plugin.svg?branch=master)

# Gradle HTL Plugin

This plugin is Gradle port of existing [Apache Sling HTL Maven Plugin](https://github.com/apache/sling-htl-maven-plugin).

It is simple Gradle Plugin for eager AEM [HTML Template Language](https://docs.adobe.com/content/help/en/experience-manager-htl/using/overview.html) (HTL) files validation. Usually, HTL files get compiled when component is rendered on AEM. If it contain any errors, those won't be spotted during deployment. This plugin performs HTL files compilation just before deployment to prevent introducing unintended bugs.

# Compatibility
This plugin has been tested with Gradle versions (5.0.0+).

# Usage

Plugin setup `buildSrc/build.gradle.kts`:

```kotlin
repositories {
    maven { url = uri("http://dl.bintray.com/cognifide/maven-public") }
}

dependencies {
    implementation("com.cognifide.gradle:htl-plugin:0.0.1")
}
```

Apply plugin in your `build.gradle.kts` script:
```kotlin
plugins {
    id("com.cognifide.gradle.htl")
}

plugins.apply("com.cognifide.gradle.htl")
```

## Running

To run plugin simply type `gradlew htlValidate` in commandline.

# Configuration

You can configure few things:
1. Directory where your HTL files are stored (to avoid redundant scanning)
2. List of supported extensions (files recognized as HTL templates)
3. Decide if plugin will fail when warning is present. By default it fails only on errors.

Sample configuration (shown values are plugin defaults):
```kotlin
htlValidation {
    directory("src/main/content/jcr_root/")
    extensions("**/*.html", "**/*.htl")
    failOnWarnings()
}
```

# Licence

**Gradle HTL Plugin** is licensed under the [Apache License, Version 2.0 (the "License")](https://www.apache.org/licenses/LICENSE-2.0.txt)
