package com.example.glasstask.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.glasstask.model.TaskItem

@Dao
interface TaskDatabaseDao {

    @Insert
    suspend fun insertTask(taskItem: TaskItem)

    @Update
    suspend fun updateTask(taskItem: TaskItem)

    @Delete
    suspend fun deleteTask(taskItem: TaskItem)

    @Query("SELECT * FROM taskDatabase")
    fun getAllTaskData() : List<TaskItem>

}