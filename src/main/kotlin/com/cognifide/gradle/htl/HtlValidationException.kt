package com.cognifide.gradle.htl

import java.lang.RuntimeException

class HtlValidationException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, throwable: Throwable) : super(message, throwable)
}