package com.HLS_user_service.service

import com.HLS_user_service.dto.request.RegisterUserRequest
import com.HLS_user_service.dto.response.UserResponse
import com.HLS_user_service.entity.User
import com.HLS_user_service.repository.UserRepository
import com.HLS_user_service.util.notNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val hashService: HashService,
) {

    fun editUser(registerUserRequestRequest: RegisterUserRequest): UserResponse {
        val user = getUser(registerUserRequestRequest.email)
        if (registerUserRequestRequest.password != null) {
            val passwordSalt = hashService.generateSalt()
            val passwordHashed = hashService.hashString(registerUserRequestRequest.password.notNull(), passwordSalt)

            user.passwordHashed = passwordHashed
            user.passwordSalt = passwordSalt
        }
        user.firstName = registerUserRequestRequest.firstName
        user.lastName = registerUserRequestRequest.lastName
        user.phone = registerUserRequestRequest.phone

        return userRepository.save(user).toResponse()
    }

    fun save(user: User): User {
        return userRepository.save(user)
    }

    fun checkIfUserExists(email: String): Boolean {
        val user = userRepository.findByEmail(email)
        return user == null
    }

    fun getUser(email: String) = userRepository.findByEmail(email) ?: throw Exception("User $email does not exists")
}