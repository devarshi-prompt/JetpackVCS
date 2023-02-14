package com.example.jetpackvcs.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast

class Constants {

    companion object {
        const val USER_REGISTERED_KEY = "isRegistered"
        fun CharSequence?.isValidEmail() =
            !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

        fun Any.toast(mContext: Context, message: String): Toast{
            return Toast.makeText(mContext,message,Toast.LENGTH_SHORT)
        }
    }
}