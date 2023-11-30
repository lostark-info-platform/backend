package org.info.lostark.auth.infra.oauth2.google.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.info.lostark.auth.command.domain.OAuth2UserData
import org.info.lostark.user.command.domain.SocialProvider


@JsonNaming(value = SnakeCaseStrategy::class)
data class GoogleUserResponse(
    val id: String,
    val email: String,
    val name: String,
) {
    fun toOAuth2UserData(): OAuth2UserData {
        return OAuth2UserData(SocialProvider.GOOGLE, id)
    }
}