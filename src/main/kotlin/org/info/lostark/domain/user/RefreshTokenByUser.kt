package org.info.lostark.domain.user

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

/**
 * 탈퇴하거나 비밀번호 변경시 유저에 대응하는 리프레시 토큰 삭제 용도
 */
@RedisHash(value = "refresh-token-by-user")
data class RefreshTokenByUser(
    @Id
    val id: Long, // userId
    val tokens: MutableList<String> = mutableListOf() // refreshToken id list
)


