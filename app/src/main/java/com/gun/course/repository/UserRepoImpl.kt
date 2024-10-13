package com.gun.course.repository

import com.gun.course.model.User
import com.gun.course.network.ApiService

class UserRepoImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(): List<User> {
        return apiService.getUser()
    }

}