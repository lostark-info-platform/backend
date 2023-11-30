package org.info.lostark.auth.command.application

import org.info.lostark.user.command.domain.RefreshToken
import org.info.lostark.user.command.domain.RefreshTokenRepository
import org.info.lostark.user.command.domain.UserRepository
import org.info.lostark.user.command.domain.getOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun rotate(userId: Long, jwt: String) {
        val user = userRepository.getOrThrow(userId)
        refreshTokenRepository.deleteAllByUserId(userId)
        refreshTokenRepository.save(RefreshToken(user, jwt))
    }

    @Transactional
    fun findByJwt(jwt: String): RefreshToken {
        return refreshTokenRepository.findByJwt(jwt)
            ?: throw IllegalArgumentException()
    }
}