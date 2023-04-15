package com.example.math.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.math.domain.TaskItem
import com.example.math.domain.TaskListRepository
import javax.inject.Inject

class TaskListRepositoryImpl  @Inject constructor(
    private val taskListDao: TaskListDao,
private val mapper: TaskListMapper
) : TaskListRepository {

    override suspend fun addTaskItem(taskItem: TaskItem) {
        taskListDao.addTaskItem(mapper.mapEntityToDbModel(taskItem))
    }

    override suspend fun deleteTaskItem(taskItem: TaskItem) {
        taskListDao.deleteTaskItem(taskItem.id)
    }

    override suspend fun editTaskItem(taskItem: TaskItem) {
        taskListDao.addTaskItem(mapper.mapEntityToDbModel(taskItem))
    }

    override suspend fun getTaskItem(taskItemId: Int): TaskItem {
        val dbModel = taskListDao.getTaskItem(taskItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getTaskList(): LiveData<List<TaskItem>> = Transformations.map(
        taskListDao.getTaskList()
    ) {
        mapper.mapListDbModelToListEntity(it)
    }


}