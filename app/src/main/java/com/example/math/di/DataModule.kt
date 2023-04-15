package com.example.math.di

import android.app.Application
import com.example.math.data.AppDatabase
import com.example.math.data.TaskListDao
import com.example.math.data.TaskListRepositoryImpl
import com.example.math.domain.TaskListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindTaskListRepository(impl: TaskListRepositoryImpl): TaskListRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideTaskListDao(
            application: Application
        ): TaskListDao {
            return AppDatabase.getInstance(application).taskListDao()
        }
    }
}