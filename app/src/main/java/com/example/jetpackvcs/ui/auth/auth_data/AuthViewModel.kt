package com.example.jetpackvcs.ui.auth.auth_data

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackvcs.utils.ApiStates
import com.example.jetpackvcs.utils.Constants.Companion.isValidEmail
import com.example.jetpackvcs.utils.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    val email = ObservableField("")
    val password = ObservableField("")
    val isRegister = MutableStateFlow(true)

    val _isLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow: StateFlow<Boolean> = _isLoadingFlow

    private val _loginValidationFlow = MutableSharedFlow<Validation>()
    val loginValidationFlow: SharedFlow<Validation> = _loginValidationFlow

    private val _loginUiStatesFlow = MutableSharedFlow<ApiStates>()
    val loginUiStatesFlow: SharedFlow<ApiStates> = _loginUiStatesFlow

    private val _registerValidationFlow = MutableSharedFlow<Validation>()
    val registerValidationFlow: SharedFlow<Validation> = _registerValidationFlow

    private val _registerUiStatesFlow = MutableSharedFlow<ApiStates>()
    val registerUiStatesFlow: SharedFlow<ApiStates> = _registerUiStatesFlow

    fun validation(view: View) {
        val validation: Validation = when {
            email.get() == "" && password.get() == "" -> {
                Validation.ValidationFailed("Please enter email and password")
            }
            email.get() == "" && password.get() != "" -> {
                Validation.ValidationFailed("Please enter email")
            }
            email.get() != "" && password.get() == "" ->{
                if (email.get().isValidEmail()){
                    Validation.ValidationFailed("Please enter password")
                } else{
                    Validation.ValidationFailed("Please enter valid email and password")
                }
            }
            email.get() != "" && password.get() != "" ->{
                if (email.get().isValidEmail()){
                    if (!isRegister.value){
                        onLoginHandler()
                    } else{
                        onRegisterHandler()
                    }
                    Validation.ValidationSuccess
                } else {
                    Validation.ValidationFailed("Invalid email")
                }
            }
            else -> Validation.Empty
        }
        viewModelScope.launch {
            if (!isRegister.value) {
                _loginValidationFlow.emit(validation)
            } else{
                _registerValidationFlow.emit(validation)
            }
        }
    }

    private fun onLoginHandler() {
        viewModelScope.launch {
            val response =
                authRepository.loginUser(email.get().toString(), password.get().toString())
            try {
                _loginUiStatesFlow.emit(ApiStates.IsLoading)
                if (response.isSuccessful) {
                    _loginUiStatesFlow.emit(ApiStates.OnSuccess(response))
                } else {
                    _loginUiStatesFlow.emit(
                        ApiStates.OnFailure(response.errorBody()?.string().toString())
                    )
                }
            } catch (e: Exception) {
                _loginUiStatesFlow.emit(ApiStates.OnFailure(e.message.toString()))
            }
        }
    }

    private fun onRegisterHandler(){
        viewModelScope.launch {
            val response = authRepository.registerUser(email.get().toString(),password.get().toString())
            try {
                _registerUiStatesFlow.emit(ApiStates.IsLoading)
                if (response.isSuccessful){
                    _registerUiStatesFlow.emit(ApiStates.OnSuccess(response))
                } else{
                    _registerUiStatesFlow.emit(ApiStates.OnFailure(response.errorBody()?.string().toString()))
                }
            } catch (e: Exception){
                _registerUiStatesFlow.emit(ApiStates.OnFailure(e.message.toString()))
            }
        }
    }
}