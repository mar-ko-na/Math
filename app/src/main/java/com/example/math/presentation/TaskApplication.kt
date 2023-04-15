package com.example.math.presentation

import android.app.Application
import com.example.math.di.DaggerApplicationComponent

class TaskApplication() : Application() {

    val component by lazy{
        DaggerApplicationComponent.factory().create(this)
    }
}