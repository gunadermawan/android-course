package com.gun.course.network

import com.gun.course.model.Post
import com.gun.course.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPost(): List<Post>

    @GET("users")
    suspend fun getUser(): List<User>

}