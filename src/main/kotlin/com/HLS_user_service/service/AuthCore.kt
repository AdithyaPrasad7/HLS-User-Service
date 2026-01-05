package com.HLS_user_service.service

import io.jsonwebtoken.Claims
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthCore {

    private fun claims(): Claims? = SecurityContextHolder.getContext().authentication?.details as? Claims

    fun getUserId(): String = SecurityContextHolder.getContext().authentication?.principal?.toString() ?: ""

    fun getEmail(): String = claims()?.get("email")?.toString() ?: ""

    fun getFirstName(): String = claims()?.get("firstName")?.toString() ?: "system"

    fun getLastName(): String = claims()?.get("lastName")?.toString() ?: ""

    fun getPhone(): String = claims()?.get("phone")?.toString() ?: ""

    fun getJti(): String = claims()?.id ?: ""

    fun getName() = (getFirstName() + getLastName())
}