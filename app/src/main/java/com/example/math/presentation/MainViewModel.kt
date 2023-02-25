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

    val taskList = MutableLiveData<List<TaskItem>>()

    fun getTaskList() {
        val list = getTaskListUseCase.getTaskList()
        taskList.value = list
    }

    fun deleteTaskList(taskItem: TaskItem){
        deleteTaskItemUseCase.deleteTaskItem(taskItem)
        getTaskList()
    }

    fun changeEnableState(taskItem: TaskItem){
        val newItem = taskItem.copy(enabled = !taskItem.enabled)
        editTaskItemUseCase.editTaskItem(newItem)
        getTaskList()
    }

}