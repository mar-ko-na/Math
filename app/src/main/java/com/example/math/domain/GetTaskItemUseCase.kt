package com.example.math.domain

class GetTaskItemUseCase(private val taskListRepository: TaskListRepository) {

    fun getTaskItem (taskItem: Int): TaskItem{
        return taskListRepository.getTaskItem(taskItem)
    }
}