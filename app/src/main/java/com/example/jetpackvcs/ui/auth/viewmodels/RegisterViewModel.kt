package com.example.jetpackvcs.ui.auth.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackvcs.ui.auth.repositories.RegisterRepository
import com.example.jetpackvcs.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository) : ViewModel() {

    private val _isLoadingFlow = MutableSharedFlow<Boolean>()
    val isLoadingFlow: SharedFlow<Boolean> = _isLoadingFlow
    suspend fun registerUser(registrationEmail: String, password: String) {
        _isLoadingFlow.emit(true)
        viewModelScope.launch {
            val response = registerRepository.registerUser(registrationEmail,password)

            if (response.isSuccessful){
                Constants.isUserRegistered = true
                Log.d("RegisterSuccessful", "registerUser: ${response.body().toString()}")
                _isLoadingFlow.emit(false)
                if (response.code() == 200){
                    Log.d("UserId", "registerUser: ${response.body()?.id.toString()}")
                    Log.d("UserToken", "registerUser: ${response.body()?.token.toString()}")
                }
            } else{
                Constants.isUserRegistered = false
                _isLoadingFlow.emit(false)
                Log.d("RegisterFailure", "registerUser: ${response.errorBody()?.string()}")
            }
        }
    }
}