package com.example.math.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.math.R
import com.example.math.domain.TaskItem

class TaskListAdapter: ListAdapter<TaskItem, TaskItemViewHolder>(TaskItemDiffCallback()) {

    var onTaskCBClickListener: ((TaskItem) -> Unit)? = null
    var onTaskItemClickListener: ((TaskItem) -> Unit)? = null
//        set(value){
//            field = value
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_task_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TaskItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TaskItemViewHolder, position: Int) {

        val taskItem = getItem(position)
        viewHolder.tvName.text = taskItem.name
        viewHolder.tvWorker.text = taskItem.description.toString()

        viewHolder.cbEnabled.setOnCheckedChangeListener(null)
        viewHolder.cbEnabled.isChecked = taskItem.enabled
        viewHolder.cbEnabled.setOnCheckedChangeListener { _, isChecked ->
            Log.d("MyLog", "cb is checked: $isChecked")
            onTaskCBClickListener?.invoke(taskItem)
        }
        viewHolder.view.setOnClickListener{
            onTaskItemClickListener?.invoke(taskItem)
        }
        


    }



    override fun getItemViewType(position: Int): Int {
//        val item = getItem(position)

        return VIEW_TYPE_ENABLED

    }



    companion object {

        const val VIEW_TYPE_ENABLED = 100

        const val MAX_POOL_SIZE = 30
    }
}