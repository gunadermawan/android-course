package com.gun.course.repository

import com.gun.course.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun getUsers(): List<User>
}