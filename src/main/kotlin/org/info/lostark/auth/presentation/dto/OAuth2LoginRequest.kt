package org.info.lostark.auth.presentation.dto

data class OAuth2LoginRequest(
    val provider: String,
    val accessToken: String
)
