package com.example.jetpackvcs.ui.auth.models

import com.google.gson.annotations.SerializedName

data class AuthenticationRequestBody(
    val email: String,
    val password: String)