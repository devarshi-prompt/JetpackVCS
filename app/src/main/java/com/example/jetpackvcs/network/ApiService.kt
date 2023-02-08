package com.example.jetpackvcs.network

import com.example.jetpackvcs.ui.auth.models.LoginResponse
import com.example.jetpackvcs.ui.auth.models.RegisterResponse
import com.example.jetpackvcs.ui.dashboard.models.User
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/register")
    suspend fun registerUser(): Response<RegisterResponse>

    @POST("api/login")
    suspend fun loginUser(): Response<LoginResponse>

    @POST("api/users")
    suspend fun getUsers(@Query("page") page: Int): Response<List<User>>

}