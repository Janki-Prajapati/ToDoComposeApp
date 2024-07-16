package com.jp.test.todocomposeapp.usecase

import com.jp.test.todocomposeapp.R
import com.jp.test.todocomposeapp.generic.UiText
import com.jp.test.todocomposeapp.generic.useCase.BaseUseCase
import com.jp.test.todocomposeapp.validators.ValidationResult

class ValidateDescriptionUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strTheDescriptionCanNotBeBlank),
            )
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}