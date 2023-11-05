package org.info.lostark.auth.command.application

import org.info.lostark.user.command.domain.Password
import org.info.lostark.user.command.domain.UserRepository
import org.info.lostark.user.command.domain.getOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ResignService(
    private val refreshTokenService: RefreshTokenService,
    private val userRepository: UserRepository,
) {
    fun resign(userId: Long, password: Password) {
        val user = userRepository.getOrThrow(userId)
        user.resign(password)
        //TODO 1. user 정보 삭제
        //TODO 2. resign유저 정보 적재
        refreshTokenService.revokeAll(userId)
    }
}