package com.example.glasstask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glasstask.model.TaskItem
import com.example.glasstask.repo.Repository
import com.example.glasstask.utility.TaskResponce
import kotlinx.coroutines.launch


class TaskViewModel(private val repository: Repository) : ViewModel() {
    private val mutableTaskData : MutableLiveData<TaskResponce<List<TaskItem>>> = MutableLiveData()
    val getAllTask : LiveData<TaskResponce<List<TaskItem>>> = mutableTaskData

    fun getAllTask(){
        val taskData = repository.getAllTaskData()
        mutableTaskData.postValue(TaskResponce.Loading())
        try {
            mutableTaskData.postValue(TaskResponce.Successful(taskData))
        }catch (e : Exception){
            mutableTaskData.postValue(TaskResponce.Error("Something went to wrong"))
        }
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