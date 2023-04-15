package com.example.math.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_items")
data class TaskItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val  id: Int,
    val  name: String,
    val  description: String,
    val  enabled: Boolean,
)
