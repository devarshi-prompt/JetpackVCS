package com.example.jetpackvcs.ui.auth.login.data

import com.example.jetpackvcs.network.ApiService
import com.example.jetpackvcs.ui.auth.login.model.AuthenticationRequestBody
import com.example.jetpackvcs.ui.auth.login.model.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun loginUser(loginEmail: String,password: String): LoginResponse {
        val response: Response<LoginResponse> = apiService.loginUser(AuthenticationRequestBody(loginEmail,password))
        return response.body()!!
    }
}