package com.example.math.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.math.domain.TaskItem
import com.example.math.presentation.TaskApplication
import javax.inject.Inject

class TaskListProvider : ContentProvider() {

    private val component by lazy {
        (context as TaskApplication).component
    }

    @Inject
    lateinit var taskListDao: TaskListDao

    @Inject
    lateinit var mapper: TaskListMapper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.", "task_items", GET_TASK_ITEMS_QUERY)
        addURI("", "task_items/#", GET_TASK_ITEM_BY_ID_QUERY)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            GET_TASK_ITEMS_QUERY -> {
                taskListDao.getTaskListCursor()
            }
            else -> {
                null
            }
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            GET_TASK_ITEMS_QUERY -> {
                if (values == null) return null
                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val description = values.getAsString("description")
                val enabled = values.getAsBoolean("enabled")
                val taskItem = TaskItem(
                    id = id,
                    name = name,
                    description = description,
                    enabled = enabled
                )
                taskListDao.addTaskItemSync(mapper.mapEntityToDbModel(taskItem))
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            GET_TASK_ITEMS_QUERY -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                return taskListDao.deleteTaskItemSync(id)
            }
        }
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object {

        private const val GET_TASK_ITEMS_QUERY = 100
        private const val GET_TASK_ITEM_BY_ID_QUERY = 101
    }
}