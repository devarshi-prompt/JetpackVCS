package com.example.jetpackvcs.ui.dashboard.data

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.jetpackvcs.ui.dashboard.models.User
import com.example.jetpackvcs.utils.ApiStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val userListRepository: UserListRepository) :
    ViewModel() {

    private val _userListUiStatesFlow = MutableSharedFlow<ApiStates>()
    val userListUiStatesFlow: SharedFlow<ApiStates> = _userListUiStatesFlow

    fun getAllUsers() {
        viewModelScope.launch {
            try {
                val response = userListRepository.getUsers(1)

                _userListUiStatesFlow.emit(ApiStates.IsLoading)
                if (response.isSuccessful) {
                    _userListUiStatesFlow.emit(ApiStates.OnSuccess(response.body()?.data))
                } else {
                    _userListUiStatesFlow.emit(
                        ApiStates.OnFailure(response.errorBody()?.string().toString()))
                }
            } catch (e: Exception) {
                _userListUiStatesFlow.emit(ApiStates.OnFailure(e.message.toString()))
            }
        }
    }

    companion object {
        @BindingAdapter("app:imageUrl")
        fun setImageUrl(view: ImageView, url: String) {
            Glide.with(view.context).load(url).into(view)
        }
    }

}