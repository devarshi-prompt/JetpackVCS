package com.example.jetpackvcs.utils

sealed class Validation{
    object ValidationSuccess: Validation()
    data class ValidationFailed(val message: String): Validation()
    object Empty: Validation()
}
