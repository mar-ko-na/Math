package com.example.math.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.math.R
import com.example.math.domain.TaskItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var taskListAdapter: TaskListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.taskList.observe(this) {
            taskListAdapter.taskList = it
        }
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_task_list)
        with(rvShopList) {
            taskListAdapter = TaskListAdapter()
            adapter = taskListAdapter
            recycledViewPool.setMaxRecycledViews(
                TaskListAdapter.VIEW_TYPE_ENABLED,
                TaskListAdapter.MAX_POOL_SIZE
            )
        }
        setupSwipeListener(rvShopList)
        setupCBClickListener()


    }

    private fun setupSwipeListener(rvTaskList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = taskListAdapter.taskList[viewHolder.bindingAdapterPosition]
                viewModel.deleteTaskItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvTaskList)
    }
    private fun setupCBClickListener() {
        taskListAdapter.onTaskCBClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}




