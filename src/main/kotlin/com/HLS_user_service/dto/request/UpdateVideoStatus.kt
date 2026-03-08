package com.HLS_user_service.dto.request

data class UpdateVideoStatus(
    val path: String,
    val token: String,
    val isValid: Boolean
)
