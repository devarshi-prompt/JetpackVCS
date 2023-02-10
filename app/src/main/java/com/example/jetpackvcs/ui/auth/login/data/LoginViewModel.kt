package com.example.jetpackvcs.ui.auth.login.data

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.viewbinding.BuildConfig
import com.example.jetpackvcs.ui.auth.login.LoginFragmentDirections
import com.example.jetpackvcs.ui.auth.login.model.Credentials
import com.example.jetpackvcs.utils.ApiStates
import com.example.jetpackvcs.utils.Constants.Companion.isValidEmail
import com.example.jetpackvcs.utils.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel() {

    val email = ObservableField("")
    val password = ObservableField("")

    val _isLoadingFlow = MutableSharedFlow<Boolean>()
    val isLoadingFlow: SharedFlow<Boolean> = _isLoadingFlow

    private val _loginValidationFlow = MutableSharedFlow<Validation>()
    val loginValidationFlow: SharedFlow<Validation> = _loginValidationFlow

    private val _loginUiStatesFlow = MutableSharedFlow<ApiStates>()
    val loginUiStatesFlow: SharedFlow<ApiStates> = _loginUiStatesFlow

    fun validation(view: View){

        val validation: Validation = when{
            email.get() == "" -> {
                Validation.validationFailed("Please enter an email")
            }
            password.get() == "" ->{
                Validation.validationFailed("Please enter a password")
            }
            email.get() != "" && password.get() != "" -> {
                if (email.get().toString().isValidEmail()){
                    onLoginHandler()
                    Validation.validationSuccess
                } else{
                    Validation.validationFailed("Invalid email")
                }
            }
            else -> Validation.Empty
        }

        viewModelScope.launch {
            _loginValidationFlow.emit(validation)
        }
    }

    private fun onLoginHandler(){
        viewModelScope.launch{
            val response = loginRepository.loginUser(email.get().toString(),password.get().toString())
            try {
                _loginUiStatesFlow.emit(ApiStates.isLoading)
                if (response.token.isNotEmpty()){
                    _loginUiStatesFlow.emit(ApiStates.onSuccess(response))
                } else{
                    _loginUiStatesFlow.emit(ApiStates.onFailure(response.error))
                }
            } catch (e: Exception){
                _loginUiStatesFlow.emit(ApiStates.onFailure(e.message.toString()))
            }
        }
    }

}