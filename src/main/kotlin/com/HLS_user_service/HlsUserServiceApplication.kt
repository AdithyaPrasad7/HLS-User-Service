package com.HLS_user_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@Configuration
@EnableConfigurationProperties
class HlsUserServiceApplication

fun main(args: Array<String>) {
	runApplication<HlsUserServiceApplication>(*args)
}
