package com.example.math.presentation

import androidx.lifecycle.ViewModel
import com.example.math.data.TaskListRepositoryImpl
import com.example.math.domain.AddTaskItemUseCase
import com.example.math.domain.EditTaskItemUseCase
import com.example.math.domain.GetTaskItemUseCase
import com.example.math.domain.TaskItem

class TaskItemViewModel: ViewModel() {

    private val repository = TaskListRepositoryImpl

    private val getTaskItemUseCase = GetTaskItemUseCase(repository)
    private val addTaskItemUseCase = AddTaskItemUseCase(repository)
    private val editTaskItemUseCase = EditTaskItemUseCase(repository)

    fun getTaskItem(taskItemId: Int){
        val item = getTaskItemUseCase.getTaskItem(taskItemId)
    }
    fun addTaskItem(inputTaskName: String?, inputDescription: String?) {
        val name = parseName(inputTaskName)
        val description = parseDescription(inputDescription)
        val fieldsValid = validateInput(name, description)
        if (fieldsValid){
            val taskItem = TaskItem(name, description,false)
            addTaskItemUseCase.addTaskItem(taskItem)
        }

    }
    fun editTaskItem(inputTaskName: String?, inputDescription: String?) {
        val name = parseName(inputTaskName)
        val description = parseDescription(inputDescription)
        val fieldsValid = validateInput(name, description)
        if (fieldsValid){
            val taskItem = TaskItem(name, description,true)
            editTaskItemUseCase.editTaskItem(taskItem)
        }
    }
    private fun parseName(inputTaskName: String?): String {
        return inputTaskName?.trim() ?: ""
    }

    private fun parseDescription(inputDescription: String?): String{
        return inputDescription?.trim() ?: ""
    }

    private fun validateInput(name: String, description: String): Boolean {
        var result = true
        if (name.isBlank()) {
//             TODO: show error input name
            result = false
        }
        if (description.isBlank()) {
//             TODO: show error input desc
            result = false
        }
        return result
    }
}