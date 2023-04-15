package com.example.math.di

import android.app.Application
import com.example.math.data.TaskListProvider
import com.example.math.presentation.MainActivity
import com.example.math.presentation.TaskItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: TaskItemFragment)

    fun inject(provider: TaskListProvider)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}