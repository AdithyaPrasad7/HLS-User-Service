package com.HLS_user_service.config

import com.HLS_user_service.entity.User
import com.HLS_user_service.util.notNull
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date
import java.security.Key
import java.util.UUID

@Component
class JwtTokenUtil {
    private val secret = "NPAw9z$&F)J@NcRfUjXn2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$&E(H+MbQeTAhWmZqN"
    private val expiration = 60000000 // milliseconds
    private val secretKey: Key = Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8))

    fun generateToken(user: User): String {
        return Jwts.builder()
            .setSubject(user.id.notNull().toString())
            .claim("email", user.email)
            .claim("firstName", user.firstName)
            .claim("lastName", user.lastName)
            .claim("phone", user.phone)
            .claim("roles", "USER")
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .setId(UUID.randomUUID().toString())
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(token: String?): Boolean =
        try {
            token != null && !isExpired(token)
        } catch (e: Exception) {
            false
        }

    private fun isExpired(token: String): Boolean =
        getClaims(token).expiration.before(Date())

    fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }
}