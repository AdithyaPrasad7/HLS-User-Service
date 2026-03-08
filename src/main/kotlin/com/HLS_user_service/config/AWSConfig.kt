package com.HLS_user_service.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.sts.StsClient


@Configuration
class AwsConfig {

    @Bean
    fun s3Client(): S3Client =
        S3Client.builder().build()

    @Bean
    fun s3Presigner(): S3Presigner =
        S3Presigner.builder().build()

    @Bean
    fun stsClient(): StsClient {
        return StsClient.create()
    }
}
