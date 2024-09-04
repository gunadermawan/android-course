package com.gun.course.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyBoundService : Service() {
    private val binder = LocalBinder()
    override fun onCreate() {
        super.onCreate()
        Log.d("boundService", "onCreate: bound service created")
    }

    inner class LocalBinder : Binder() {
        fun getService(): MyBoundService = this@MyBoundService
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d("boundService", "onBind: service bound")
        return binder
    }

    fun performOperation() {
        Log.d("boundService", "performOperation started ")
    }
}