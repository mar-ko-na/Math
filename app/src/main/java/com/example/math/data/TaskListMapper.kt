package com.example.math.data

import com.example.math.domain.TaskItem
import javax.inject.Inject

class TaskListMapper @Inject constructor() {

    fun mapEntityToDbModel(taskItem: TaskItem) = TaskItemDbModel(
        id = taskItem.id,
        description = taskItem.description,
        name = taskItem.name,
        enabled = taskItem.enabled
    )

    fun mapDbModelToEntity(taskItemDbModel: TaskItemDbModel) = TaskItem(
        id = taskItemDbModel.id,
        name = taskItemDbModel.name,
        description = taskItemDbModel.description,
        enabled = taskItemDbModel.enabled
    )

    fun mapListDbModelToListEntity(list: List<TaskItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}