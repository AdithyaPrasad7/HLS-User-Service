package com.HLS_user_service.api

import com.HLS_user_service.dto.response.Response
import com.HLS_user_service.service.TokenDetailsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping
@ResponseBody
class TokenDetailsController(
    private val tokenDetailsService: TokenDetailsService
) {

    @GetMapping("/validate-token")
    fun isTokenValid(@RequestParam token: String): ResponseEntity<Response> {
        val isValid = tokenDetailsService.isTokenValid(token)
        return ResponseEntity(Response().data(isValid).success(), HttpStatus.OK)
    }

    @PostMapping("/api/token-details")
    fun saveTokenDetails(
        @RequestParam userId: Long,
        @RequestParam expireIn: Long
    ): ResponseEntity<Response> {
        val tokenDetails = tokenDetailsService.saveTokenDetails(userId, expireIn)
        return ResponseEntity(Response().data(tokenDetails).success(), HttpStatus.CREATED)
    }
}