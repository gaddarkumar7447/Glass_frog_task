package com.example.glasstask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glasstask.model.TaskItem
import com.example.glasstask.repo.Repository
import kotlinx.coroutines.launch


class TaskViewModel(private val repository: Repository) : ViewModel() {
    fun getAllTask() : LiveData<List<TaskItem>>{
        return repository.getAllTaskData()
    }

    fun insertTask(task: TaskItem){
        viewModelScope.launch {
            repository.insertTaskData(task)
        }
    }

    fun updateTask(task: TaskItem){
        viewModelScope.launch {
            repository.updateTaskData(task)
        }
    }

    fun deleteTask(task: TaskItem){
        viewModelScope.launch {
            repository.deleteTaskData(task)
        }
    }
}