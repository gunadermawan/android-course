package com.gun.course.di

import com.gun.course.network.ApiService
import com.gun.course.viewmodel.UserViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    viewModel { UserViewmodel(get()) }
}