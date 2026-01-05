package com.HLS_user_service.api

import com.HLS_user_service.dto.request.RegisterUserRequest
import com.HLS_user_service.dto.response.Response
import com.HLS_user_service.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
@ResponseBody
class UserController(private val userService: UserService) {

    @PutMapping
    fun editUser(@RequestBody request: RegisterUserRequest): ResponseEntity<Response> {
        val user = userService.editUser(request)
        return ResponseEntity(Response().data(user).success(), HttpStatus.OK)
    }
}