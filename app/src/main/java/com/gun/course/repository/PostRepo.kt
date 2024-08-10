package com.gun.course.repository

import com.gun.course.model.Post
import com.gun.course.network.ApiService

class PostRepo(private val apiService: ApiService) {
    suspend fun getPosts():List<Post>{
        return apiService.getPost()
    }
}