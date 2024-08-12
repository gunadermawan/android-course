package com.gun.course.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


//Define Keys

val USERNAME_KEY = stringPreferencesKey("username")
val IS_LOGIN_KEY = booleanPreferencesKey("isLoggedIn")

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

suspend fun saveToDataStore(context: Context, username: String, isLogin: Boolean) {
    context.dataStore.edit {
        it[USERNAME_KEY] = username
        it[IS_LOGIN_KEY] = isLogin
    }
}

fun getUserPreferencesFlow(context: Context): Flow<Pair<String, Boolean>> {
    return context.dataStore.data
        .map {
            val username = it[USERNAME_KEY] ?: ""
            val isLogin = it[IS_LOGIN_KEY] ?: false
            Pair(username, isLogin)
        }
}

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
