package com.example.math.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.math.data.TaskListRepositoryImpl
import com.example.math.domain.*

class MainViewModel : ViewModel() {

    private val repository = TaskListRepositoryImpl

    private val getTaskListUseCase = GetTaskListUseCase(repository)
    private val deleteTaskItemUseCase = DeleteTaskItemUseCase(repository)
    private val editTaskItemUseCase = EditTaskItemUseCase(repository)

    val taskList = getTaskListUseCase.getTaskList()



    fun deleteTaskItem(taskItem: TaskItem){
        deleteTaskItemUseCase.deleteTaskItem(taskItem)
    }

    fun changeEnableState(taskItem: TaskItem){
        val newItem = taskItem.copy(enabled = !taskItem.enabled)
        editTaskItemUseCase.editTaskItem(newItem)
    }

}