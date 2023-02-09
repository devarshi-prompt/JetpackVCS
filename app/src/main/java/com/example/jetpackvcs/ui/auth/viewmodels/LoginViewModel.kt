package com.example.jetpackvcs.ui.auth.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackvcs.ui.auth.repositories.LoginRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel() {

    private val _isLoadingFlow = MutableSharedFlow<Boolean>()
    val isLoadingFlow: SharedFlow<Boolean> = _isLoadingFlow
    fun loginUser(loginEmail: String, password: String){
        viewModelScope.launch {
            _isLoadingFlow.emit(true)
            val response = loginRepository.loginUser(loginEmail,password)

            if (response.isSuccessful){
                _isLoadingFlow.emit(false)
                if (response.code() == 200){
                    Log.d("LoginSuccess", "loginUser: ${response.body()?.token.toString()}")
                }
            } else{
                _isLoadingFlow.emit(false)
                Log.e("LoginFailure", "loginUser: ${response.errorBody()?.string()}")
            }
        }
    }
}