package com.HLS_user_service.config

import com.HLS_user_service.service.UserService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class MyUserDetailsService(
    private val userService: UserService
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.getUser(username)
        return User
            .withUsername(user.email)
            .password(user.passwordHashed)
            .authorities(emptyList())
            .build()
    }
}