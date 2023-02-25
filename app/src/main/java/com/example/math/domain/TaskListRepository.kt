package com.example.math.domain

interface TaskListRepository {

    fun addTaskItem (taskItem: TaskItem)

    fun deleteTaskItem (taskItem: TaskItem)

    fun editTaskItem (taskItem: TaskItem)

    fun getTaskItem (taskItemId: Int): TaskItem

    fun getTaskList(): List<TaskItem>



    }