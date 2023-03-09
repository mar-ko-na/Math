package com.example.math.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.math.R
import com.example.math.domain.TaskItem

class TaskListAdapter: RecyclerView.Adapter<TaskListAdapter.TaskItemViewHolder>() {

    var count = 0
    var ert = 0
    var taskList = listOf<TaskItem>()
        set(value){
            val callback = TaskListDiffCallBack(taskList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_task_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TaskItemViewHolder(view)
    }

    @Suppress("UNUSED_EXPRESSION")
    override fun onBindViewHolder(viewHolder: TaskItemViewHolder, position: Int) {

        val taskItem = taskList[position]
        viewHolder.tvName.text = taskItem.name
        viewHolder.tvWorker.text = taskItem.worker.toString()

        viewHolder.cbEnabled.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("MyLog", "cb is checked: $isChecked")

        }


    }

    override fun onViewRecycled(viewHolder: TaskItemViewHolder) {
        super.onViewRecycled(viewHolder)
        viewHolder.tvName.text = ""
        viewHolder.tvWorker.text = ""
//        viewHolder.tvName.setTextColor(
//            ContextCompat.getColor(
//                viewHolder.view.context,
//                android.R.color.holo_green_dark
//            )
//        )
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = taskList[position]

        return VIEW_TYPE_ENABLED

    }

    class TaskItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvWorker = view.findViewById<TextView>(R.id.tv_worker)
        val cbEnabled = view.findViewById<CheckBox>(R.id.cb_enabled)
    }


    companion object {

        const val VIEW_TYPE_ENABLED = 100

        const val MAX_POOL_SIZE = 30
    }
}