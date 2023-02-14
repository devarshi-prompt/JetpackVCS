package com.example.jetpackvcs.utils

sealed class ApiStates{

    //Use generics
    data class OnSuccess(val response: Any): ApiStates()
    data class OnFailure(val error: String): ApiStates()
    object IsLoading: ApiStates()
}
