package com.example.glasstask.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.glasstask.R
import com.example.glasstask.activity.MainActivity
import com.example.glasstask.database.TaskDataBase
import com.example.glasstask.model.TaskItem

class TaskClassManager(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.d("Running", "doWork method running")
        //val instance = TaskDataBase.getDataBaseInstance(applicationContext).getTaskDao()
        //val tasks: List<TaskItem> = instance.getAllTask()

        /*for (task in tasks) {
            val currentTime = System.currentTimeMillis()
            val thirtyMinutesInMillis = 30 * 60 * 1000

            if (!task.isComplete && currentTime - task.taskCreateTime <= thirtyMinutesInMillis) {
                //Log.d("Running", task.toString())
                showNotification(task)
            }
        }*/

        return Result.success()
    }

    private fun showNotification(task: TaskItem) {
        val channelId = "task_notification_channel"
        val notificationId = 1

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_MUTABLE)

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.baseline_add_task_24)
            .setContentTitle(task.title)
            .setContentText(task.description)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Task Notifications", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}