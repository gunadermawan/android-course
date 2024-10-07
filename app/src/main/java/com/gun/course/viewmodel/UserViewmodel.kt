package com.gun.course.viewmodel

import androidx.lifecycle.ViewModel
import com.gun.course.model.User
import com.gun.course.repository.UserRepository
import kotlinx.coroutines.flow.StateFlow

class UserViewmodel : ViewModel() {
    private val repository = UserRepository()
    val user: StateFlow<User> = repository.getUserData()
}