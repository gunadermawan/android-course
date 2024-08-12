package com.gun.course.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gun.course.local.database.DbHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel(context: Context) : ViewModel() {
    private val dbHelper = DbHelper(context)
    private val _names = MutableStateFlow<List<String>>(emptyList())
    val name: StateFlow<List<String>> = _names

    fun loadNames() {
        _names.value = dbHelper.getAllNames()
    }

    fun addName(name: String) {
        dbHelper.insertName(name)
        loadNames() //refresh list
    }
}

class UserViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}