package com.example.jetpackvcs.ui.auth.register

import android.content.SharedPreferences.Editor
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository) : ViewModel() {

    private val _isLoadingFlow = MutableSharedFlow<Boolean>()
    val isLoadingFlow: SharedFlow<Boolean> = _isLoadingFlow
    suspend fun registerUser(registrationEmail: String, password: String, preferenceEditor: Editor?) {
        _isLoadingFlow.emit(true)
        viewModelScope.launch {
            val response = registerRepository.registerUser(registrationEmail,password)
            if (response.isSuccessful){
                _isLoadingFlow.emit(false)
                if (BuildConfig.DEBUG)
                    Log.d("RegisterSuccessful", "registerUser: ${response.body().toString()}")
                if (response.code() == 200)
                    preferenceEditor?.putBoolean("userRegistered",true)?.apply()
            } else{
                _isLoadingFlow.emit(false)
                if (BuildConfig.DEBUG)
                    Log.e("RegisterFailure", "registerUser: ${response.errorBody()?.string()}")
            }
        }
    }
}