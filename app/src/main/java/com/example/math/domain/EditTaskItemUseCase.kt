package com.example.math.domain

import javax.inject.Inject

class EditTaskItemUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository
) {

    suspend fun editTaskItem (taskItem: TaskItem){
        taskListRepository.editTaskItem(taskItem)
    }
}