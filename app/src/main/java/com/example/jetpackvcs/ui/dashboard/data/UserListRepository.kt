package com.example.jetpackvcs.ui.dashboard.data

import com.example.jetpackvcs.network.ApiService
import com.example.jetpackvcs.ui.dashboard.models.User
import com.example.jetpackvcs.ui.dashboard.models.UserListResponse
import retrofit2.Response
import javax.inject.Inject

class UserListRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUsers(page: Int): Response<UserListResponse>{
        return apiService.getUsers(page)
    }
}