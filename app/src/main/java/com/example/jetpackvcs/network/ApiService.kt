package com.example.jetpackvcs.network

import com.example.jetpackvcs.ui.auth.models.LoginResponse
import com.example.jetpackvcs.ui.auth.models.AuthenticationRequestBody
import com.example.jetpackvcs.ui.auth.models.RegisterResponse
import com.example.jetpackvcs.ui.dashboard.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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