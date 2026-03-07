package com.HLS_user_service.repository

import com.HLS_user_service.entity.TokenDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenDetailsRepository: JpaRepository<TokenDetails, Long> {
    fun findByToken(token: String): TokenDetails?
}