package com.example.glasstask.repo

import androidx.lifecycle.LiveData
import com.example.glasstask.database.TaskDatabaseDao
import com.example.glasstask.model.TaskItem

class Repository(private val taskDatabaseDao: TaskDatabaseDao) {
    suspend fun insertTaskData(task : TaskItem){
        taskDatabaseDao.insertTask(task)
    }

    suspend fun updateTaskData(task: TaskItem){
        taskDatabaseDao.updateTask(task)
    }

    suspend fun deleteTaskData(task: TaskItem){
        taskDatabaseDao.deleteTask(task)
    }

    fun getAllTaskData() : LiveData<List<TaskItem>>{
        return taskDatabaseDao.getAllTaskData()
    }
}