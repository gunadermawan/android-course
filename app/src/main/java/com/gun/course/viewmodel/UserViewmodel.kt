package com.gun.course.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gun.course.model.User
import com.gun.course.repository.UserRepository
import com.gun.course.usecase.GetUserUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewmodel(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    fun fetchUsers() {
        viewModelScope.launch {
            val result = getUserUseCase.execute()
            _users.value = result
        }
    }
}