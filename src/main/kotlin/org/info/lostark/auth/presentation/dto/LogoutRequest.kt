package org.info.lostark.auth.presentation.dto

data class LogoutRequest(
    val refreshToken: String
)