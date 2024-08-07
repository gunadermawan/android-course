package com.gun.course.network

import com.gun.course.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPost():List<Post>
}