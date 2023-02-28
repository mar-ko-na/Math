package com.example.math.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.math.R
import com.example.math.domain.TaskItem

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskItemViewHolder>() {

    var taskList = listOf<TaskItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_task_disabled,
            parent,
            false
        )
        return TaskItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TaskItemViewHolder, position: Int) {
        val taskItem = taskList[position]
        val status = if (taskItem.enabled){
            "Active"
        }else{
            "Not active"
        }

        viewHolder.cbEnabled.setOnCheckedChangeListener { _, _ ->
            true
        }
        if (taskItem.enabled) {
            viewHolder.tvName.text = "${taskItem.name} $status"
            viewHolder.tvWorker.text = taskItem.worker.toString()
            viewHolder.tvName.setTextColor(ContextCompat.getColor(viewHolder.view.context, android.R.color.holo_orange_dark))
        }
    }

    override fun onViewRecycled(viewHolder: TaskItemViewHolder) {
        super.onViewRecycled(viewHolder)
        viewHolder.tvName.text = ""
        viewHolder.tvWorker.text = ""
        viewHolder.tvName.setTextColor(ContextCompat.getColor(viewHolder.view.context, android.R.color.white))
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class TaskItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvWorker = view.findViewById<TextView>(R.id.tv_worker)
        val cbEnabled = view.findViewById<CheckBox>(R.id.cb_enabled)
    }
}