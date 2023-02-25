package com.example.math.domain

data class TaskItem(

    val name: String,
    val worker: Int,
    val enabled: Boolean,
    val time: Int,
    var id: Int = UNDEFINED_ID,
){
    companion object{

        const val UNDEFINED_ID = -1
    }
}
