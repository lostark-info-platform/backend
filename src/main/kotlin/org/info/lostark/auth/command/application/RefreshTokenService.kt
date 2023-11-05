package org.info.lostark.auth.command.application

import org.info.lostark.user.command.domain.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val refreshTokenByUserRepository: RefreshTokenByUserRepository,
    private val userRepository: UserRepository
) {
    fun create(userId: Long): String {
        val token = refreshTokenRepository.save(RefreshToken(UUID.randomUUID().toString(), userId))
        val tokenByUser = refreshTokenByUserRepository
            .findByIdOrNull(token.userId)
            ?: RefreshTokenByUser(userId)
        tokenByUser.tokens.add(token.id)
        refreshTokenByUserRepository.save(tokenByUser)
        return token.id
    }

    fun revoke(refreshToken: String) {
        val token = refreshTokenRepository.getOrThrow(refreshToken)
        refreshTokenRepository.delete(token)
    }

    fun revokeAll(userId: Long) {
        val tokens = refreshTokenByUserRepository.findByIdOrNull(userId)?.tokens
        if (!tokens.isNullOrEmpty()) {
            refreshTokenRepository.deleteAllById(tokens)
        }
    }

    fun getUser(token: String): User {
        val refreshToken = refreshTokenRepository.getOrThrow(token)
        return userRepository.getOrThrow(refreshToken.userId)
    }
}