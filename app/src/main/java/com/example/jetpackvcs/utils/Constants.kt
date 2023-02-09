package com.example.jetpackvcs.utils

import android.util.Patterns

class Constants {

    companion object{
        var isUserRegistered = false
        fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}