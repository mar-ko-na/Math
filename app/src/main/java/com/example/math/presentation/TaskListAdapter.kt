package com.example.math.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.math.R
import com.example.math.databinding.FragmentTaskItemBinding
import com.example.math.databinding.ItemTaskEnabledBinding
import com.example.math.domain.TaskItem

class TaskListAdapter: ListAdapter<TaskItem, TaskItemViewHolder>(TaskItemDiffCallback()) {

    var onTaskCBClickListener: ((TaskItem) -> Unit)? = null
    var onTaskItemClickListener: ((TaskItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_task_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return TaskItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: TaskItemViewHolder, position: Int) {
        val taskItem = getItem(position)
        val binding = viewHolder.binding

        binding.root.setOnClickListener{
            onTaskItemClickListener?.invoke(taskItem)
        }

        when (binding) {
            is ItemTaskEnabledBinding -> {
                binding.taskItem = taskItem
                binding.cbEnabled.setOnCheckedChangeListener { _, isChecked ->
                    Log.d("MyLog", "cb is checked: $isChecked")
                    onTaskCBClickListener?.invoke(taskItem)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_ENABLED
    }

    companion object {

        const val VIEW_TYPE_ENABLED = 100

        const val MAX_POOL_SIZE = 30
    }
}