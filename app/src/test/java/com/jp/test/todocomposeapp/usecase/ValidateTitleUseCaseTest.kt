package com.jp.test.todocomposeapp.usecase

import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ValidateTitleUseCaseTest{

    private lateinit var validateTitleUseCase : ValidateTitleUseCase
    @Before
    fun setUp(){
        validateTitleUseCase = ValidateTitleUseCase()
    }

    @Test
    fun `empty title returns false`(){
        val result = validateTitleUseCase.execute(input = "")
        Truth.assertThat(result.successful).isFalse()
    }

    @Test
    fun `valid title returns true`(){
        val result = validateTitleUseCase.execute(input = "title")
        Truth.assertThat(result.successful).isTrue()
    }
}