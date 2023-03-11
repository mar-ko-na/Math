package com.example.math.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.math.R

class TaskItemActivity : AppCompatActivity() {
    private lateinit var viewModel: TaskItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_item)
        viewModel.errorInputName.value
    }
}