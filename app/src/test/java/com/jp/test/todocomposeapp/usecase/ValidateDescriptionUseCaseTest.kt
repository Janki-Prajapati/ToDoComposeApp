package com.jp.test.todocomposeapp.usecase

import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ValidateDescriptionUseCaseTest{
    private lateinit var validateDescriptionUseCase : ValidateDescriptionUseCase

    @Before
    fun setUp(){
        validateDescriptionUseCase = ValidateDescriptionUseCase()
    }

    @Test
    fun `empty description returns false`(){
        val result = validateDescriptionUseCase.execute(input = "")
        Truth.assertThat(result.successful).isFalse()
    }

    @Test
    fun `valid description returns true`(){
        val result = validateDescriptionUseCase.execute(input = "description")
        Truth.assertThat(result.successful).isTrue()
    }
}