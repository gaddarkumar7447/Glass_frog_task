package com.example.glasstask.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.glasstask.R
import com.example.glasstask.activity.MainActivity
import com.example.glasstask.database.TaskDataBase
import com.example.glasstask.database.TaskDatabaseDao
import com.example.glasstask.model.TaskItem
import com.example.glasstask.workmanager.TaskClassManager
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class TaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //workerSchedule()
    }

    private fun workerSchedule() {
        val constraints = androidx.work.Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val trackTaskRequest = PeriodicWorkRequestBuilder<TaskClassManager>(30, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(trackTaskRequest)
    }
}