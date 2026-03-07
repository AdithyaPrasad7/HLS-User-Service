package com.HLS_user_service.service

import com.HLS_user_service.dto.response.TokenDetailsResponse
import com.HLS_user_service.entity.TokenDetails
import com.HLS_user_service.repository.TokenDetailsRepository
import com.HLS_user_service.util.exception.BadRequest
import com.HLS_user_service.util.addDays
import org.springframework.stereotype.Service
import java.util.Date

@Service
class TokenDetailsService(
    private val tokenDetailsRepository: TokenDetailsRepository,
    private val userService: UserService
) {
    fun getTokenDetails(token: String) =
        tokenDetailsRepository.findByToken(token) ?: throw BadRequest("Invalid token")

    fun saveTokenDetails(userId: Long, expireIn: Long) {
        val expirtDate = Date().addDays(expireIn.toLong())
        tokenDetailsRepository.save(TokenDetails(user = userService.getUserById(userId), expiry = expirtDate))
    }

    fun isTokenValid(token: String): TokenDetailsResponse {
        val tokenDetails = getTokenDetails(token)

        return TokenDetailsResponse(tokenDetails.isValid && tokenDetails.expiry.after(Date()),
            tokenDetails.expiry, tokenDetails.user.firstName + " " + tokenDetails.user.lastName
        )
    }
}