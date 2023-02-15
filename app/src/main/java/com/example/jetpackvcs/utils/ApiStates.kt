package com.example.jetpackvcs.utils

import com.example.jetpackvcs.ui.dashboard.models.User
import retrofit2.Response

sealed class ApiStates{

    //Use generics
    data class OnSuccess(val response: Response<*>): ApiStates()

    data class OnFailure(val error: String): ApiStates()
    object IsLoading: ApiStates()
}
