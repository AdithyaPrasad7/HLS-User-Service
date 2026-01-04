package com.HLS_user_service.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date
import java.security.Key

@Component
class JwtTokenUtil {
    private val secret = "NPAw9z$&F)J@NcRfUjXn2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$&E(H+MbQeTAhWmZq\n"
    private val expiration = 60000000 // milliseconds

    fun generateToken(email: String): String {
        val secretKey = Keys.hmacShaKeyFor(secret.toByteArray())
        // Create JWT token
        return Jwts.builder()
            .setSubject(email)
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(secretKey).compact()
    }

    fun getEmail(jwt: String): String {
        try {
            val jwtBody = getBodyFromToken(jwt)
            return jwtBody
        } catch (e: Exception) {
            return "401 Unauthorized"
        }
    }

    fun isTokenValid(jwt: String?): Boolean {
        try {
            if (jwt == null) {
                return false
            }
            getBodyFromToken(jwt)
            return true;
        } catch (e: Exception) {
            return false
        }
    }

    private fun getSignInKey(): Key? {
        return Keys.hmacShaKeyFor(secret.toByteArray())
    }

    private fun getBodyFromToken(token: String): String {
        val body = Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
        return body.toString()
    }
}