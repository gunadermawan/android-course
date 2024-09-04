package com.gun.course.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.gun.course.R

class MyForegroundService : Service() {

    companion object {
        const val CHANNEL_ID = "ForegroundServiceChannel"
        const val NOTIFICATION_ID = 1

    }

    override fun onCreate() {
        super.onCreate()
        createNotificatoinChannel()
        Log.d("foreground service", "onCreate: service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("foreground service", "onStartCommand: service started")
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Running in the foreground")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("foreground service", "onDestroy: service destroyed")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun createNotificatoinChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "foreground service channel", NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}