package com.example.math.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.math.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPause() {
        super.onPause()
    }
}