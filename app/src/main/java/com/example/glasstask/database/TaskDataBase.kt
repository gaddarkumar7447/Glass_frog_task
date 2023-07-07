package com.example.glasstask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.glasstask.model.TaskItem

@Database(entities = [TaskItem::class], version = 1, exportSchema = false)
abstract class TaskDataBase : RoomDatabase(){
    abstract fun getTaskDao() : TaskDatabaseDao

    companion object{
        private var INSTANCE : TaskDataBase?= null
        fun getDataBaseInstance(context: Context) : TaskDataBase{
            synchronized(this){
                if (INSTANCE == null){
                    val instance = Room.databaseBuilder(context, TaskDataBase::class.java, "taskDatabase").build()
                    INSTANCE = instance
                }
            }
            return INSTANCE!!
        }
    }
}