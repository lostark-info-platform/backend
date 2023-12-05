package org.info.lostark.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties(
    val secret: String,
    val accessExpiration: Long,
    val refreshExpiration: Long
)
