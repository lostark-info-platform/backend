package org.info.lostark.common.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.data.redis")
data class RedisConfigProperties(
    val host: String,
    val port: Int
)