package com.jp.test.todocomposeapp.database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    suspend fun addTask(task: Task) {
        taskDao.addTask(task = task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task = task)
    }

    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    fun getTask(id: Int): Flow<Task> {
        return taskDao.getTask(id = id)
    }

    suspend fun deleteAllTasks(){
        taskDao.deleteAllTasks()
    }

    fun getTasksWithId(id : Int): Flow<List<Task>> {
        return taskDao.getTasksWithId(id)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task = task)
    }
}