package com.example.math.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.math.domain.DeleteTaskItemUseCase
import com.example.math.domain.EditTaskItemUseCase
import com.example.math.domain.GetTaskListUseCase
import com.example.math.domain.TaskItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getTaskListUseCase: GetTaskListUseCase,
    private val deleteTaskItemUseCase : DeleteTaskItemUseCase,
    private val editTaskItemUseCase : EditTaskItemUseCase
) : ViewModel(){

    val taskList = getTaskListUseCase.getTaskList()

    fun deleteTaskItem(taskItem: TaskItem){
        viewModelScope.launch {
            deleteTaskItemUseCase.deleteTaskItem(taskItem)
        }

    }

    fun changeEnableState(taskItem: TaskItem){
        viewModelScope.launch {
            val newItem = taskItem.copy(enabled = !taskItem.enabled)
            editTaskItemUseCase.editTaskItem(newItem)
        }
    }

}