package com.HLS_user_service.service

data class SessionResponse(
    val path: String,
    val bucket: String,
    val credentials: TemporaryCredentials
)

data class TemporaryCredentials(
    val accessKey: String,
    val secretKey: String,
    val sessionToken: String,
    val expiresIn: Int
)
