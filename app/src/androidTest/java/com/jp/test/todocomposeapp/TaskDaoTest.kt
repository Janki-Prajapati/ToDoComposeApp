package com.jp.test.todocomposeapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.jp.test.todocomposeapp.database.Task
import com.jp.test.todocomposeapp.database.TaskDao
import com.jp.test.todocomposeapp.database.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@SmallTest
class TaskDaoTest {

    private lateinit var database: TaskDatabase
    private lateinit var taskDao: TaskDao


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java).allowMainThreadQueries()
                .build()
        taskDao = database.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun cleanup() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun addTask_returnTrue() = runTest {
        val task = Task(id = 1, title = "Home work", priority = 1, description = "Description")
        taskDao.addTask(task)
        val job = async(Dispatchers.IO) {
            taskDao.getAllTasks().collect {
                assertThat(it).contains(task)
//                assertEquals(it.size, 1)
            }
        }

        job.cancelAndJoin()
    }

    @Test
    @Throws(Exception::class)
    fun addTask_multiple_returnTrue() = runTest {
        val task = Task(id = 1, title = "Home work", priority = 1, description = "Description")
        val task1 = Task(id = 2, title = "Home work1", priority = 2, description = "Description1")
        val task2 = Task(id = 3, title = "Home work2", priority = 3, description = "Description2")
        taskDao.addTask(task)
        taskDao.addTask(task1)
        taskDao.addTask(task2)
        val job = async(Dispatchers.IO) {
            taskDao.getAllTasks().collect {
                assertThat(it.size == 3)
            }
        }

        job.cancelAndJoin()
    }

    @Test
    @Throws(Exception::class)
    fun updateTask_returnTrue() = runTest {
        val task = Task(id = 1, title = "Home work", priority = 1, description = "Description")
        taskDao.addTask(task)
        val task1 = Task(id = 1, title =  "Test Title", priority = 1, description = "Description")
        taskDao.updateTask(task1)
        val job = async(Dispatchers.IO) {
            taskDao.getAllTasks().collect {
                assertThat(it).contains(task1)
            }
        }

        job.cancelAndJoin()
    }

    @Test
    fun delete_task_returns_true() = runTest{
        val task = Task(id = 1, title = "Home work", priority = 1, description = "Description")
        val task1 = Task(id = 1, title =  "Test Title", priority = 1, description = "Description")
        taskDao.addTask(task1)
        taskDao.addTask(task)
        taskDao.deleteTask(task1)
        val job = async(Dispatchers.IO) {
            taskDao.getAllTasks().collect {
                assertThat(it.size == 1)
            }
        }

        job.cancelAndJoin()
    }

    @Test
    fun get_task_by_id_returns_true() = runTest{
        val task = Task(id = 1, title = "Home work", priority = 1, description = "Description")
        taskDao.addTask(task)
        val job = async(Dispatchers.IO) {
            taskDao.getTask(1).collect {
                assertThat(it.priority == 1)
            }
        }

        job.cancelAndJoin()
    }
}