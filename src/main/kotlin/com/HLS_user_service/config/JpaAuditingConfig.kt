package com.HLS_user_service.config

import com.HLS_user_service.service.AuthCore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider") // Enable JPA auditing
class JpaAuditingConfig(private val authCore: AuthCore) {
    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware<String> {
            Optional.of(authCore.getName())
        }
    }
}