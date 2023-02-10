package com.example.jetpackvcs.utils

import com.example.jetpackvcs.ui.auth.login.model.LoginResponse

sealed class ApiStates{
    data class onSuccess(val response: LoginResponse): ApiStates()
    data class onFailure(val error: String): ApiStates()
    object isLoading: ApiStates()
}
