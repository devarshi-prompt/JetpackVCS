package com.example.jetpackvcs.ui.auth.register

import com.example.jetpackvcs.network.ApiService
import com.example.jetpackvcs.ui.auth.login.model.AuthenticationRequestBody
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val apiService: ApiService){
    suspend fun registerUser(registrationEmail: String,password: String) = apiService.registerUser(
        AuthenticationRequestBody(registrationEmail,password)
    )
}