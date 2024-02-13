package org.info.lostark.common.security

import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.info.lostark.auth.command.application.JwtProvider
import org.info.lostark.user.command.domain.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter


class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authorization: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                filterChain.doFilter(request, response)
                return
            }
            val jwt = authorization.substring(7)
            val user = userRepository.findByIdOrNull(jwtProvider.extractUserId(jwt))
            if (user != null && SecurityContextHolder.getContext().authentication == null) {
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(user, null, user.authorities)
            }

            filterChain.doFilter(request, response)

        } catch (e: JwtException) {
            response.contentType = "application/json;charset=utf-8"
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.message)
        } catch (e: Exception) {
            throw e
        }
    }
}