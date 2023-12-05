package org.info.lostark.auth.command.application

import org.info.lostark.auth.command.application.dto.TokenResponse
import org.info.lostark.user.command.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val refreshTokenService: RefreshTokenService,
    private val jwtProvider: JwtProvider
) {
    @Transactional
    fun generateTokenByRefreshToken(refreshToken: String): TokenResponse {
        if (!jwtProvider.isValidToken(refreshToken)) {
            throw IllegalArgumentException()
        }
        val user = findUserByRefreshToken(refreshToken)

        return tokenResponse(user)
    }

    private fun findUserByRefreshToken(refreshToken: String): User {
        return refreshTokenService
            .findByJwt(refreshToken)
            .user
    }

    private fun tokenResponse(user: User): TokenResponse {
        val accessToken = jwtProvider.generateAccessToken(user)
        val refreshToken = jwtProvider.generateRefreshToken(user)
        refreshTokenService.rotate(user.id, refreshToken)
        return TokenResponse(accessToken, refreshToken)
    }
}