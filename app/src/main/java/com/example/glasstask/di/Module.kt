package com.example.glasstask.di

import android.content.Context
import androidx.room.Room
import com.example.glasstask.database.TaskDataBase
import com.example.glasstask.database.TaskDatabaseDao
import com.example.glasstask.repo.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun getContext(@ApplicationContext context: Context) : Context{
        return context
    }

    @Provides
    @Singleton
    fun getDatabase(context: Context) : TaskDatabaseDao{
        return Room.databaseBuilder(context, TaskDataBase::class.java, "taskDatabase").build().getTaskDao()
    }

    @Provides
    fun getRepository(taskDatabaseDao: TaskDatabaseDao) : Repository{
        return Repository(taskDatabaseDao)
    }

}