package com.jp.test.todocomposeapp.database

import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {
    fun addTask(task: Task)
    fun deleteTask(task: Task)
    fun getAllTasks(): Flow<List<Task>>
    fun getTask(id: Int): Task
}