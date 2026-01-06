package com.HLS_user_service.api

import com.HLS_user_service.dto.request.LoginRequest
import com.HLS_user_service.dto.request.RegisterUserRequest
import com.HLS_user_service.dto.response.Response
import com.HLS_user_service.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@ResponseBody
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Response> {
        val jwtToken =  authService.login(loginRequest)
        return ResponseEntity(Response().data(jwtToken).success(), HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@RequestBody registerUserRequest: RegisterUserRequest): ResponseEntity<Response> {
        authService.registerUser(registerUserRequest)
        return ResponseEntity(Response().success(), HttpStatus.CREATED)
    }
}