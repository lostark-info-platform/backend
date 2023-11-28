package org.info.lostark.auth.infra.oauth2.google.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(value = SnakeCaseStrategy::class)
data class GoogleToken(
    val accessToken: String,
    val expiresIn: Int,
    val scope: String,
    val tokenType: String
)
