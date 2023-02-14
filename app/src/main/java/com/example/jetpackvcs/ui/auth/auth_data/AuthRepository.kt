package com.example.jetpackvcs.ui.auth.auth_data

import com.example.jetpackvcs.network.ApiService
import com.example.jetpackvcs.ui.auth.login.model.LoginResponse
import com.example.jetpackvcs.ui.auth.register.model.RegisterResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun loginUser(loginEmail: String, password: String): Response<LoginResponse> {
        return apiService.loginUser(AuthenticationRequestBody(loginEmail, password))
    }

    suspend fun registerUser(registrationEmail: String,password: String): Response<RegisterResponse>{
        return apiService.registerUser(AuthenticationRequestBody(registrationEmail,password))
    }
}