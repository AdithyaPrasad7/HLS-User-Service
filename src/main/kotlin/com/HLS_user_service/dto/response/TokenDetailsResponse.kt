package com.HLS_user_service.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

data class TokenDetailsResponse(
    @get:JsonProperty("isValid")
    val isValid: Boolean,
    val expiry: Date,
    val userName: String
)
