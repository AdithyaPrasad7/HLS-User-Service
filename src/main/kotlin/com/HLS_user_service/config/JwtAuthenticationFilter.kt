package com.HLS_user_service.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtil,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = authHeader.substring(7)

        try {
            if (!jwtTokenUtil.isTokenValid(jwtToken)) {
                filterChain.doFilter(request, response)
                return
            }

            if (SecurityContextHolder.getContext().authentication == null) {
                val claims = jwtTokenUtil.getClaims(jwtToken)

                val authentication = UsernamePasswordAuthenticationToken(
                    claims.subject,
                    null,
                    emptyList()
                )

                authentication.details = claims

                SecurityContextHolder.getContext().authentication = authentication
            }

            filterChain.doFilter(request, response)

        } catch (ex: Exception) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT")
        }
    }
}
