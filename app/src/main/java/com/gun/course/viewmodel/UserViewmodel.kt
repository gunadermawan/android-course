package com.gun.course.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gun.course.model.User
import com.gun.course.network.ApiService
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class UserViewmodel(private val apiService: ApiService) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val compositeDisposable = CompositeDisposable()

    fun fetchUsers() {
        viewModelScope.launch {
            flow {
                emit(apiService.getUser())
            }.catch { e ->
                _error.value = e.message
                Log.e("TAG", "fetchUsers: ${e.message}", )
            }
                .collect {
                    _users.value = it
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}