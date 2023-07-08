package com.example.glasstask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("taskDatabase")
data class TaskItem(
    @PrimaryKey(autoGenerate = true) val id : Long,
    val title : String,
    val description : String,
    var taskCreateTime : Long,
    var isComplete : Boolean = false
)