package com.gun.course.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

fun setDailyAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
//    set alarm 9
    val calender = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 9)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    if (calender.timeInMillis < System.currentTimeMillis()) {
        calender.add(Calendar.DATE, 1)
    }
//    set alarm
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calender.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}