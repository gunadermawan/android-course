package com.gun.course.usecase

import com.gun.course.model.User
import com.gun.course.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(): List<User> {
        return userRepository.getUsers()
    }
}