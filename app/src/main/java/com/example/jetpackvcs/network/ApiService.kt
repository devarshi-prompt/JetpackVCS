package com.example.jetpackvcs.network

import com.example.jetpackvcs.ui.auth.auth_data.AuthenticationRequestBody
import com.example.jetpackvcs.ui.auth.login.model.LoginResponse
import com.example.jetpackvcs.ui.auth.register.model.RegisterResponse
import com.example.jetpackvcs.ui.dashboard.models.UserListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun registerUser(@Body authenticationRequestBody: AuthenticationRequestBody): Response<RegisterResponse>

    @POST("login")
    suspend fun loginUser(@Body authenticationRequestBody: AuthenticationRequestBody): Response<LoginResponse>

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<UserListResponse>

}