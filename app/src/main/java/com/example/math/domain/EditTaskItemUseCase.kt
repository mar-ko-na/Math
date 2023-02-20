package com.example.math.domain

class EditTaskItemUseCase(private val taskListRepository: TaskListRepository) {

    fun editTaskItem (taskItem: TaskItem){
        taskListRepository.editTaskItem(taskItem)
    }
}