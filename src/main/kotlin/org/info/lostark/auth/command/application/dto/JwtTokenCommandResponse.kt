package org.info.lostark.auth.command.application.dto

data class JwtTokenCommandResponse(
    val token: String,
    val refreshToken: String
)
