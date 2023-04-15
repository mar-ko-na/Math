package com.example.math.domain

import javax.inject.Inject

class DeleteTaskItemUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository
) {

    suspend fun deleteTaskItem (taskItem: TaskItem){
        taskListRepository.deleteTaskItem(taskItem)
    }
}