package com.jp.test.todocomposeapp.validators


import com.google.common.truth.Truth.assertThat
import org.junit.Test


class AddTaskValidatorsTest{

    @Test
    fun `empty description returns false`(){
        val result = AddTaskValidators.validateFields(description = "", title = "title", priority =  "low")
        assertThat(result).isFalse()
    }

    @Test
    fun `empty title returns false`(){
        val result = AddTaskValidators.validateFields(description = "description", title = "", priority =  "low")
        assertThat(result).isFalse()
    }

    @Test
    fun `empty priority returns false`(){
        val result = AddTaskValidators.validateFields(description = "description", title = "title", priority =  "")
        assertThat(result).isFalse()
    }

    @Test
    fun `valid data returns true`(){
        val result = AddTaskValidators.validateFields(description = "description", title = "title", priority =  "low")
        assertThat(result).isTrue()
    }
}