package com.example.math.domain

class GetTaskListUseCase(private val taskListRepository: TaskListRepository) {

    fun getTaskList(): List<TaskItem> {
        return taskListRepository.getTaskList()
    }
}