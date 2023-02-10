package com.example.jetpackvcs.utils

sealed class Validation{
    object validationSuccess: Validation()
    data class validationFailed(val message: String): Validation()
    object Empty: Validation()
}
