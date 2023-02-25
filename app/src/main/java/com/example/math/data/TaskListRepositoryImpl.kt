package com.example.math.data

import com.example.math.domain.TaskItem
import com.example.math.domain.TaskListRepository

object TaskListRepositoryImpl: TaskListRepository {

    private val taskList = mutableListOf<TaskItem>()

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10){
            val item = TaskItem("Name $i", i, true, 2)
            addTaskItem(item)
        }
    }

    override fun addTaskItem(taskItem: TaskItem) {
        if (taskItem.id == TaskItem.UNDEFINED_ID) {
            taskItem.id = autoIncrementId++
        }
        taskList.add(taskItem)
     }

    override fun deleteTaskItem(taskItem: TaskItem) {
        taskList.remove(taskItem)
    }

    override fun editTaskItem(taskItem: TaskItem) {
        val oldElement = getTaskItem(taskItem.id)
        taskList.remove(oldElement)
        addTaskItem(taskItem)

    }

    override fun getTaskItem(taskItemId: Int): TaskItem {
        return taskList.find {
            it.id == taskItemId
        } ?: throw RuntimeException("Element with id $taskItemId not found")
    }

    override fun getTaskList(): List<TaskItem> {
        return taskList.toList()
    }


}