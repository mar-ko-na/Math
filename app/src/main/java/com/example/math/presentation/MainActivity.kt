package com.example.math.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.math.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.taskList.observe(this) {
            Log.d("MyLog", it.toString())
            if (count == 0) {
                val item = it[0]
                count++
                viewModel.changeEnableState(item)
            }
        }
    }

}