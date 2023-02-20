package com.example.math.domain

data class TaskItem(
    val id: Int,
    val name: String,
    val worker: String,
    val time: Int,
    val enabled: Boolean
)
