package com.HLS_user_service.repository

import com.HLS_user_service.entity.VideoDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VideoDetailsRepository: JpaRepository<VideoDetails, Long> {
    fun findByPath(path: String): VideoDetails?
}