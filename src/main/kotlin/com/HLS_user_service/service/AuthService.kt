package com.HLS_user_service.service

import com.HLS_user_service.config.JwtTokenUtil
import com.HLS_user_service.dto.request.RegisterUserRequest
import com.HLS_user_service.dto.request.LoginRequest
import com.HLS_user_service.entity.User
import com.HLS_user_service.util.notNull
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val hashService: HashService,
    private val jwtTokenUtil: JwtTokenUtil,
    private val authCore: AuthCore
) {

    fun registerUser(registerUserRequestRequest: RegisterUserRequest) {
        if (!userService.checkIfUserExists(registerUserRequestRequest.email)) {
            throw Exception("User ${registerUserRequestRequest.email} already exists")
        }

        val passwordSalt = hashService.generateSalt()
        val passwordHashed = hashService.hashString(registerUserRequestRequest.password.notNull(), passwordSalt)

        val user = User(firstName = registerUserRequestRequest.firstName, lastName = registerUserRequestRequest.lastName, email = registerUserRequestRequest.email,
            phone = registerUserRequestRequest.phone, passwordHashed = passwordHashed, passwordSalt = passwordSalt)

        userService.save(user)
    }

    fun login(loginRequest: LoginRequest): String {
        val user = userService.getUser(loginRequest.email)
        if (!hashService.validateHash(loginRequest.password, user.passwordSalt, user.passwordHashed))
        {
            throw Exception("Wrong password")
        }

        return jwtTokenUtil.generateToken(user)
    }

    fun getLoggedInUser(): User {
        val email = authCore.getEmail()
        return userService.getUser(email)
    }
}