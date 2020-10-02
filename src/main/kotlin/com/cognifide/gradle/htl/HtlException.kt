package com.cognifide.gradle.htl

import org.gradle.api.GradleException

class HtlException : GradleException {
    constructor(message: String) : super(message)

    constructor(message: String, throwable: Throwable) : super(message, throwable)
}