package com.gun.course.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

class CountingService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private var count = 0
    private val interval = 1000L

    private val runnable = object : Runnable {
        override fun run() {
            count++
            Log.d("Counting Service", "run counter:$count ")
            handler.postDelayed(this, interval)
        }

    }

    override fun onCreate() {
        super.onCreate()
        Log.d("Counting Service", "onCreate: service created")
        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Counting Service", "onCreate: service destroyed")
        handler.removeCallbacks(runnable)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}