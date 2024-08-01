package com.jp.test.todocomposeapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jp.test.todocomposeapp.database.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    var taskToUpdate by mutableStateOf<Task?>(null)
        private set

    fun addTaskToUpdate(task: Task) {
        taskToUpdate = task
    }

    fun removeTaskToUpdate() {
        taskToUpdate = null
    }
}