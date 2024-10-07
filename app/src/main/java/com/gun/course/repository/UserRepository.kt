package com.gun.course.repository

import com.gun.course.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserRepository {
    fun getUserData(): StateFlow<User> {
        return MutableStateFlow(User("John", "john@email.com"))
    }
}