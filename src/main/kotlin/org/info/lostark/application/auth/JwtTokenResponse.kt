package org.info.lostark.application.auth

data class JwtTokenResponse(
    val token: String,
    val refreshToken: String
)
