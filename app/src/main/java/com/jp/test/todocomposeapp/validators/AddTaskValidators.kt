package com.jp.test.todocomposeapp.validators

object AddTaskValidators {

    fun validateFields(title: String, description: String, priority: String): Boolean {
        return if (title.isEmpty() || description.isEmpty() || priority.isEmpty()) false
        else true
    }
}