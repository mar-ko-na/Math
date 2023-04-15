package com.example.math.domain

data class TaskItem(

    val name: String,
//    val worker: String,
    val description: String,
    val enabled: Boolean,
//    val period: Int,
    var id: Int = UNDEFINED_ID,
){
    companion object{

        const val UNDEFINED_ID = 0
    }
}
