package org.info.lostark.infra.oauth2.google

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.info.lostark.domain.oauth2.OAuth2Id
import org.info.lostark.domain.oauth2.OAuth2Provider
import org.info.lostark.domain.oauth2.OAuth2User


@JsonNaming(value = SnakeCaseStrategy::class)
data class GoogleUserResponse(
    val id: String,
    val email: String,
    val name: String,
) {
    fun toEntity(): OAuth2User {
        return OAuth2User(
            OAuth2Id(id, OAuth2Provider.GOOGLE),
            email,
            name
        )
    }
}