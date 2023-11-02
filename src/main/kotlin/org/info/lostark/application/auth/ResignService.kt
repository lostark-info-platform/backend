package org.info.lostark.application.auth

import org.info.lostark.application.user.ResignRequest
import org.info.lostark.domain.user.UserRepository
import org.info.lostark.domain.user.getOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ResignService(
    private val refreshTokenService: RefreshTokenService,
    private val userRepository: UserRepository,
) {
    fun resign(userId: Long, request: ResignRequest) {
        val user = userRepository.getOrThrow(userId)
        user.resign(request.password)
        //TODO 1. user 정보 삭제
        //TODO 2. resign유저 정보 적재
        refreshTokenService.revokeAll(userId)
    }
}