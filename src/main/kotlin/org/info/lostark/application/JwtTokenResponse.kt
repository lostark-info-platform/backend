package org.info.lostark.application

data class JwtTokenResponse(
    val token: String,
    val refreshToken: String
)
