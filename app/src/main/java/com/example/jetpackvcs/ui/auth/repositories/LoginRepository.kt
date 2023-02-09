package com.example.jetpackvcs.ui.auth.repositories

import com.example.jetpackvcs.network.ApiService
import com.example.jetpackvcs.ui.auth.models.AuthenticationRequestBody
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun loginUser(loginEmail: String,password: String) = apiService.loginUser(AuthenticationRequestBody(loginEmail,password))
}