package com.example.math.presentation

import android.app.GameState.MODE_UNKNOWN
import android.content.Context
import android.content.Intent
import android.content.Intent.parseIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.math.R
import com.example.math.domain.TaskItem
import com.google.android.material.textfield.TextInputLayout

class TaskItemActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonBack: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = TaskItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_item)
        parseIntent()
        viewModel = ViewModelProvider(this)[TaskItemViewModel::class.java]
        initViews()
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
//        viewModel.errorInputName.value = false
    }

    private fun observeViewModel() {
        viewModel.errorInputDescription.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputDescription()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchEditMode() {
        viewModel.getTaskItem(shopItemId)
        viewModel.taskItem.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.description.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editTaskItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addTaskItem(etName.text?.toString(), etCount.text?.toString())
        }
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
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_TASK_ITEM_ID, TaskItem.UNDEFINED_ID)
        }
    }

    private fun initViews() {
        tilName = findViewById(R.id.til_taskName)
        tilCount = findViewById(R.id.til_description)
        etName = findViewById(R.id.et_taskName)
        etCount = findViewById(R.id.et_description)
        buttonSave = findViewById(R.id.b_save)
        buttonBack = findViewById(R.id.b_back)
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_TASK_ITEM_ID = "extra_task_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun mewIntentAddItem(context: Context): Intent {
            val intent = Intent(context, TaskItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }
        fun mewIntentEditItem(context: Context, taskItemId: Int): Intent {
            val intent = Intent(context, TaskItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_TASK_ITEM_ID, taskItemId)
            return intent
        }
    }
}