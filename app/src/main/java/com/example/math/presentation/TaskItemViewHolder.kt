package com.example.math.presentation

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.math.R

class TaskItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val tvName = view.findViewById<TextView>(R.id.tv_name)
    val tvWorker = view.findViewById<TextView>(R.id.tv_worker)
    val cbEnabled = view.findViewById<CheckBox>(R.id.cb_enabled)
}