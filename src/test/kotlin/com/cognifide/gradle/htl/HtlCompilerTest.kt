package com.cognifide.gradle.htl

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class HtlCompilerTest {
    private val underTest = HtlCompiler(File(""))

    @Test
    fun whenValidHtl_noErrorReturns() {
        //given
        val validHtlFile = File(javaClass.getResource("/valid.htl").toURI())
        //when
        val result = underTest.compileHTLScripts(listOf(validHtlFile))
        //then
        assertTrue(result.isEmpty())
    }
}