package org.info.lostark.domain.user

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "refresh-token", timeToLive = 3600L * 24L * 14L)
data class RefreshToken(
    @Id
    val id: String,
    val userId: Long
)
