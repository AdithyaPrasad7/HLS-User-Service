package com.HLS_user_service.dto.request

data class RegisterUserRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val password: String? = null
)
