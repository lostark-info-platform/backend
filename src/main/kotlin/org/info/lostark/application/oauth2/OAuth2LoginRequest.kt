package org.info.lostark.application.oauth2

data class OAuth2LoginRequest(
    val provider: String,
    val accessToken: String
)
