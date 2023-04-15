package com.example.math.presentation

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.math.R
import com.example.math.databinding.ActivityMainBinding
import com.example.math.domain.TaskItem
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), TaskItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var taskListAdapter: TaskListAdapter
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy{
        (application as TaskApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.taskList.observe(this) {
            taskListAdapter.submitList(it)
        }
        binding.buttonAddTaskItem.setOnClickListener {
            if (isOnePaneMode()){
                val intent = TaskItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(TaskItemFragment.newInstanceAddItem())
            }
        }
        thread {
            val cursor = contentResolver.query(
                Uri.parse("content://com.example.math/task_items"),
                null,
                null,
                null,
                null,
                null,
            )
            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val description = cursor.getString(cursor.getColumnIndexOrThrow("count"))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled")) > 0
                val taskItem = TaskItem(
                    id = id,
                    name = name,
                    description = description,
                    enabled = enabled
                )
                Log.d("MainActivity", taskItem.toString())
            }
            cursor?.close()
        }

    }

    override  fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return binding.taskItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.task_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        with(binding.rvTaskList) {
            taskListAdapter = TaskListAdapter()
            adapter = taskListAdapter
            recycledViewPool.setMaxRecycledViews(
                TaskListAdapter.VIEW_TYPE_ENABLED,
                TaskListAdapter.MAX_POOL_SIZE
            )
        }
        setupSwipeListener(binding.rvTaskList)
//        https://android-tools.ru/coding/dobavlyaem-knopki-pri-svajpe-v-recyclerview/
//        https://droidbyme.medium.com/android-recyclerview-with-swipe-layout-ec62caedf694
        setupCBClickListener()
        setupClickListener()


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
                val item = taskListAdapter.currentList[viewHolder.bindingAdapterPosition]
//                viewModel.deleteTaskItem(item)
                thread {
                    contentResolver.delete(
                        Uri.parse("content://com.example.math/task_items"),
                        null,
                        arrayOf((item.id.toString()))
                    )
                }
                val intent = TaskItemActivity.newIntentEditItem(rvTaskList.context, item.id)
                startActivity(intent)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvTaskList)
    }

    private fun setupClickListener() {
        taskListAdapter.onTaskItemClickListener = {
            if (isOnePaneMode()){
                val intent = TaskItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            }else {
                launchFragment(TaskItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupCBClickListener() {
        taskListAdapter.onTaskCBClickListener = {
            viewModel.changeEnableState(it)

        }
    }
}




