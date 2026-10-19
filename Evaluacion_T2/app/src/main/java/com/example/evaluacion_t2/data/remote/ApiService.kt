package com.example.evaluacion_t2.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<ApiUser>
}