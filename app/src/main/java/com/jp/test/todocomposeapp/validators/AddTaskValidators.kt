package com.jp.test.todocomposeapp.validators

import com.jp.test.todocomposeapp.R
import com.jp.test.todocomposeapp.generic.UiText

object AddTaskValidators {

    fun validateFields(title: String, description: String, priority: String): ValidationResult {

        if (title.isEmpty()) {
            return ValidationResult(successful = false, errorMessage =UiText.StringResource(resId = R.string.strTheTitleCanNotBeBlank))
        } else if (description.isEmpty()) {
            return ValidationResult(successful = false, errorMessage = UiText.StringResource(resId = R.string.strTheDescriptionCanNotBeBlank))
        } else if (priority.isEmpty()) {
            return ValidationResult(successful = false, errorMessage = UiText.StringResource(resId = R.string.strThePriorityCanNotBeBlank))
        }
        return ValidationResult(successful = true, errorMessage = null)
    }
}