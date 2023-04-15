package com.example.math.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.math.data.TaskListRepositoryImpl
import com.example.math.domain.AddTaskItemUseCase
import com.example.math.domain.EditTaskItemUseCase
import com.example.math.domain.GetTaskItemUseCase
import com.example.math.domain.TaskItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskItemViewModel @Inject constructor(
    private val getTaskItemUseCase : GetTaskItemUseCase,
            private val addTaskItemUseCase : AddTaskItemUseCase,
            private val editTaskItemUseCase : EditTaskItemUseCase
) : ViewModel(){

    private  val _errorInputName  = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private  val _errorInputDescription  = MutableLiveData<Boolean>()
    val errorInputDescription: LiveData<Boolean>
        get() = _errorInputDescription

    private val _taskItem = MutableLiveData<TaskItem>()
    val taskItem: LiveData<TaskItem>
        get() = _taskItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getTaskItem(taskItemId: Int){
        viewModelScope.launch {
            val item = getTaskItemUseCase.getTaskItem(taskItemId)
            _taskItem.value = item
        }
    }
    fun addTaskItem(inputTaskName: String?, inputDescription: String?) {
        val name = parseName(inputTaskName)
        val description = parseDescription(inputDescription)
        val fieldsValid = validateInput(name, description)
        if (fieldsValid){
            viewModelScope.launch {
                val taskItem = TaskItem(name, description, false)
                addTaskItemUseCase.addTaskItem(taskItem)
                finishWork()
            }
        }

    }
    fun editTaskItem(inputTaskName: String?, inputDescription: String?) {
        val name = parseName(inputTaskName)
        val description = parseDescription(inputDescription)
        val fieldsValid = validateInput(name, description)
        if (fieldsValid){
            _taskItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, description = description)
                    editTaskItemUseCase.editTaskItem(item)
                    finishWork()
                }
            }

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
            _errorInputName.value = true
            result = false
        }
        if (description.isBlank()) {
             _errorInputDescription.value = true
            result = false
        }
        return result
    }

     fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputDescription() {
        _errorInputDescription.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit

    }
}