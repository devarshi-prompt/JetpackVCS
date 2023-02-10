package com.example.jetpackvcs.utils

import android.util.Patterns

class Constants {

    companion object{
        fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}