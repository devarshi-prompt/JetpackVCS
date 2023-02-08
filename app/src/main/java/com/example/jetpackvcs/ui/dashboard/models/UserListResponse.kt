package com.example.jetpackvcs.ui.dashboard.models

data class UserListResponse(
	val perPage: Int,
	val total: Int,
	val data: List<User>,
	val page: Int,
	val totalPages: Int,
	val support: Support
)

data class User(
	val lastName: String,
	val id: Int,
	val avatar: String,
	val firstName: String,
	val email: String
)

data class Support(
	val text: String,
	val url: String
)

