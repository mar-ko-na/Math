package com.example.math.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.math.R
import com.example.math.domain.TaskItem

class TaskItemActivity : AppCompatActivity(), TaskItemFragment.OnEditingFinishedListener {

    private var screenMode = MODE_UNKNOWN
    private var taskItemId = TaskItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    override fun onEditingFinished() {
        finish()
    }

    private fun launchRightMode() {
        val  fragment = when (screenMode) {
            MODE_EDIT -> TaskItemFragment.newInstanceEditItem(taskItemId)
            MODE_ADD -> TaskItemFragment.newInstanceAddItem()
            else -> throw java.lang.RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.task_item_container,fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_TASK_ITEM_ID)) {
                throw RuntimeException("Param task item id is absent")
            }
            taskItemId = intent.getIntExtra(EXTRA_TASK_ITEM_ID, TaskItem.UNDEFINED_ID)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_TASK_ITEM_ID = "extra_task_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, TaskItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }
        fun newIntentEditItem(context: Context, taskItemId: Int): Intent {
            val intent = Intent(context, TaskItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_TASK_ITEM_ID, taskItemId)
            return intent
        }
    }
}