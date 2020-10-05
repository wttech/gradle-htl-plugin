![Cognifide logo](https://assets.cognifide.com/github/cognifide-logo.png)

[![Gradle Status](https://gradleupdate.appspot.com/Cognifide/gradle-htl-plugin/status.svg?random=123)](https://gradleupdate.appspot.com/Cognifide/gradle-htl-plugin/status)
[![Apache License, Version 2.0, January 2004](docs/apache-license-badge.svg)](http://www.apache.org/licenses/)
![Travis Build](https://travis-ci.org/Cognifide/gradle-htl-plugin.svg?branch=master)

# Gradle HTL Plugin

Gradle equivalent to [Apache Sling HTL Maven Plugin](https://github.com/apache/sling-htl-maven-plugin).

# Compatibility

Tested with Gradle 6.0.0 and above.

# Setup

Plugin is released in Gradle Plugin Portal. See notes from [there](https://plugins.gradle.org/plugin/com.cognifide.htl).

## Usage

Simply run the task `gradlew htlValidate`.

# Configuration

Sample configuration (shown values are plugin defaults):

```kotlin
htl {
    sourceDir("src/main/content/jcr_root")
    sourceFilter {
        include("**/*.html", "**/*.htl")
        exclude("**/some-subdir/*")
    }
}

tasks {
    htlValidate {
        /*
        failOnWarnings() // or via property: htl.validator.failOnWarnings=true
        printIssues() // or via property: htl.validator.printIssues=true
        enabled = false // or via property: htl.validator.enabled=false
        */
    }
}
```

# Licence

**Gradle HTL Plugin** is licensed under the [Apache License, Version 2.0 (the "License")](https://www.apache.org/licenses/LICENSE-2.0.txt)
