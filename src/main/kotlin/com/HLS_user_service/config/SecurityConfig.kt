package com.HLS_user_service.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import java.security.SecureRandom

@Configuration
@EnableWebSecurity
class SecurityConfig() {
    @Bean
    open fun securityFilterChain(
        http: HttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter
    ): SecurityFilterChain {

        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/auth/**").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
            .cors { cors ->
                cors.configurationSource {
                    val corsConfiguration = CorsConfiguration()
                    corsConfiguration.allowedOrigins = listOf("*")
                    corsConfiguration.allowedMethods = listOf("*")
                    corsConfiguration.allowedHeaders = listOf("*")
                    corsConfiguration.allowCredentials = true
                    corsConfiguration

                }
            }

        return http.build()
    }

    @Bean
    fun secureRandom(): SecureRandom = SecureRandom()
}