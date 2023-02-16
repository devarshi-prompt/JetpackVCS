package com.example.jetpackvcs.utils

sealed class ApiStates<out T>{

    //Use generics
    data class OnSuccess<T>(val response: T): ApiStates<T>()
    data class OnFailure(val error: String): ApiStates<Nothing>()
    object IsLoading: ApiStates<Nothing>()
}
