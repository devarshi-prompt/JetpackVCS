package com.example.jetpackvcs.network

import com.example.jetpackvcs.ui.auth.login.model.LoginResponse
import com.example.jetpackvcs.ui.auth.login.model.AuthenticationRequestBody
import com.example.jetpackvcs.ui.auth.register.RegisterResponse
import com.example.jetpackvcs.ui.dashboard.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun registerUser(@Body authenticationRequestBody: AuthenticationRequestBody): Response<RegisterResponse>

    @POST("login")
    suspend fun loginUser(@Body authenticationRequestBody: AuthenticationRequestBody): Response<LoginResponse>

    @POST("users")
    suspend fun getUsers(@Query("page") page: Int): Response<List<User>>

}