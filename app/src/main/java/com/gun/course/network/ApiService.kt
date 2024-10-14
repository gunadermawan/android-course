package com.gun.course.network

import com.gun.course.model.Post
import com.gun.course.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPost(): List<Post>

    @GET("users")
    fun getUser(): Single<List<User>>

}