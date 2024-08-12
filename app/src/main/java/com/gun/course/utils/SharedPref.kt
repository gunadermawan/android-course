package com.gun.course.utils

import android.content.Context

    fun savePreference(context: Context, userName: String, isLogin: Boolean) {
        val sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("username", userName)
            putBoolean("isLoggedIn", isLogin)
            apply()
        }
    }

    fun getSharedPreference(context: Context): Pair<String, Boolean> {
        val sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("username", "") ?: ""
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        return Pair(userName, isLoggedIn)
    }
