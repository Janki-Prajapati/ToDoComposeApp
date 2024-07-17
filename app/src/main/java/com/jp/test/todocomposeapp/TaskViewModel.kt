package com.jp.test.todocomposeapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.test.todocomposeapp.database.Task
import com.jp.test.todocomposeapp.database.TaskRepository
import com.jp.test.todocomposeapp.generic.UiText
import com.jp.test.todocomposeapp.models.Priority
import com.jp.test.todocomposeapp.ui.theme.ColorGreen
import com.jp.test.todocomposeapp.ui.theme.ColorRed
import com.jp.test.todocomposeapp.ui.theme.ColorYellow
import com.jp.test.todocomposeapp.usecase.ValidateDescriptionUseCase
import com.jp.test.todocomposeapp.usecase.ValidateTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    /*
    * Database operations
    *
    * */

    private val _dataList: MutableStateFlow<List<Task>> = MutableStateFlow<List<Task>>(emptyList())
    val dataList get() = _dataList.asStateFlow()

    fun insertDataToDb(task: Task) {
        viewModelScope.launch {
            taskRepository.addTask(task = task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            taskRepository.deleteAllTasks()
        }
    }

    fun getAllTasks() {
        viewModelScope.launch {
            taskRepository.getAllTasks().collectLatest {
                _dataList.emit(it)
            }

        }
    }

    fun getTasksWithId(id: Int) {

        viewModelScope.launch {
            taskRepository.getTasksWithId(id).collectLatest {
                _dataList.emit(it)
            }

        }

    }

    //UI logic from here
    val _priorityList = MutableStateFlow(
        listOf(
            Priority(1, "High", ColorRed, isSelected = false),
            Priority(2, "Medium", ColorYellow, isSelected = false),
            Priority(3, "Low", ColorGreen, isSelected = true)
        )
    )

    val priorityList: List<Priority> get() = _priorityList.value


    fun updatePriority(updatedValue: String) {
        _priorityList.value = priorityList.map {
            if (it.name == updatedValue) {
                it.copy(isSelected = true)
            } else {
                it.copy(isSelected = false)
            }
        }
    }


    var formState by mutableStateOf(TaskState())

    init {
        onEvent(TaskEvent.PriorityChanged(_priorityList.value.find { it.isSelected }?.id ?: -1))
    }

    private val validateTitleUseCase = ValidateTitleUseCase()
    private val validateDescriptionUseCase = ValidateDescriptionUseCase()

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.TitleChanged -> {
                formState = formState.copy(title = event.title)
                validateTitle()
            }

            is TaskEvent.DescriptionChanged -> {
                formState = formState.copy(description = event.description)
                validateDescription()
            }

            is TaskEvent.PriorityChanged -> {
                formState = formState.copy(priorityId = event.priorityId)
            }
        }
    }

    fun validateTitle(): Boolean {
        val titleRequest = validateTitleUseCase.execute(formState.title)
        formState = formState.copy(titleError = titleRequest.errorMessage)
        return titleRequest.successful
    }

    fun validateDescription(): Boolean {
        val descriptionRequest = validateDescriptionUseCase.execute(formState.description)
        formState = formState.copy(descriptionError = descriptionRequest.errorMessage)
        return descriptionRequest.successful
    }


    sealed class TaskEvent {
        data class TitleChanged(val title: String) : TaskEvent()
        data class PriorityChanged(val priorityId: Int) : TaskEvent()
        data class DescriptionChanged(val description: String) : TaskEvent()
    }

    data class TaskState(
        val title: String = "",
        val titleError: UiText? = null,
        val priorityId: Int = -1,
        val priorityError: UiText? = null,
        val description: String = "",
        val descriptionError: UiText? = null
    )
}