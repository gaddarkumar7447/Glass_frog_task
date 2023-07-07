package com.example.glasstask.utility

sealed class TaskResponce<T>(val data : T ?= null, val message : String ?= null){
    class Loading<T> : TaskResponce<T>()
    class Successful<T>(data: T?= null) : TaskResponce<T>(data = data)
    class Error<T>(errorMessage : String) : TaskResponce<T>(message = errorMessage)
}