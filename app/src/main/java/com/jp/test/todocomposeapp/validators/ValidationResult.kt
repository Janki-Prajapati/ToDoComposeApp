package com.jp.test.todocomposeapp.validators

import com.jp.test.todocomposeapp.generic.UiText

data class ValidationResult(    val successful: Boolean,
                                val errorMessage: UiText? = null)
