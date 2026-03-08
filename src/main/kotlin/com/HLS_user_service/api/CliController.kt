package com.HLS_user_service.api

import com.HLS_user_service.dto.request.UpdateVideoStatus
import com.HLS_user_service.dto.response.Response
import com.HLS_user_service.service.TokenDetailsService
import com.HLS_user_service.service.VideoDetailsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cli")
@ResponseBody
class CliController(
    private val videoDetailsService: VideoDetailsService,
    private val tokenDetailsService: TokenDetailsService
) {

    @GetMapping("/validate-token")
    fun isTokenValid(@RequestParam token: String): ResponseEntity<Response> {
        val isValid = tokenDetailsService.isTokenValid(token)
        return ResponseEntity(Response().data(isValid).success(), HttpStatus.OK)
    }

    @GetMapping("/create-session")
    fun createSession(@RequestParam token: String): ResponseEntity<Response> {
        val data = videoDetailsService.createUploadSessionForCLI(token)
        return ResponseEntity(Response().data(data).success(), HttpStatus.OK)
    }

    @PutMapping("/update-video-details")
    fun updateVideoDetails(@RequestBody updateVideoStatus: UpdateVideoStatus): ResponseEntity<Response> {
        videoDetailsService.updateVideoDetailsForCLI(updateVideoStatus)
        return ResponseEntity(Response().success().data(true), HttpStatus.OK)
    }
}