package com.example.jetpackvcs.ui.dashboard.models

import com.google.gson.annotations.SerializedName

data class UserListResponse(
	val perPage: Int,
	val total: Int,
	val data: List<User>,
	val page: Int,
	val totalPages: Int,
	val support: Support)

data class User(
	@field:SerializedName("last_name")
	val lastName: String,
	val id: Int,
	val avatar: String,
	@field:SerializedName("first_name")
	val firstName: String,
	val email: String)

data class Support(
	val text: String,
	val url: String)