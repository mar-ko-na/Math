package com.example.math.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.math.R
import com.example.math.domain.TaskItem

class TaskListAdapter: RecyclerView.Adapter<TaskListAdapter.TaskItemViewHolder>() {

    var count = 0
    var taskList = listOf<TaskItem>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var onTaskItemClickListener: ((TaskItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        Log.d("MyLog", "onCreateViewHolder, count: ${++count}")
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_task_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_task_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TaskItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TaskItemViewHolder, position: Int) {
        val taskItem = taskList[position]
        viewHolder.tvName.text = taskItem.name
        viewHolder.tvWorker.text = taskItem.worker.toString()

//        viewHolder.cbEnabled.setOnClickListener {
//            onTaskItemClickListener?.invoke(taskItem)
//            true
//        }

    }

    override fun onViewRecycled(viewHolder: TaskItemViewHolder) {
        super.onViewRecycled(viewHolder)
        viewHolder.tvName.text = ""
        viewHolder.tvWorker.text = ""
        viewHolder.tvName.setTextColor(
            ContextCompat.getColor(
                viewHolder.view.context,
                android.R.color.holo_green_dark
            )
        )
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = taskList[position]
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    class TaskItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvWorker = view.findViewById<TextView>(R.id.tv_worker)
        val cbEnabled = view.findViewById<ImageView>(R.id.cb_enabled)
    }

    interface  OnTaskItemClickListener{

        fun onTaskItemClick(taskItem: TaskItem)
    }

    companion object {

        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 30
    }
}