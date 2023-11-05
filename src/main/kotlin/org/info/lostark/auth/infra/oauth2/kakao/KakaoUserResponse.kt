package org.info.lostark.auth.infra.oauth2.kakao

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.info.lostark.auth.command.domain.OAuth2Id
import org.info.lostark.auth.command.domain.OAuth2Provider
import org.info.lostark.auth.command.domain.OAuth2User
import java.time.LocalDateTime

@JsonNaming(value = SnakeCaseStrategy::class)
data class KakaoUserResponse(
    val id: Long,
    val connectedAt: LocalDateTime,
    val properties: KakaoUserProperties,
    val kakaoAccount: KakaoAccount,
) {
    fun toEntity(): OAuth2User {
        return OAuth2User(
            OAuth2Id(id.toString(), OAuth2Provider.KAKAO),
            kakaoAccount.email,
            properties.nickname
        )
    }
}

@JsonNaming(value = SnakeCaseStrategy::class)
data class KakaoUserProperties(
    val nickname: String
)

@JsonNaming(value = SnakeCaseStrategy::class)
data class KakaoAccount(
    val profileNicknameNeedsAgreement: Boolean,
    val profile: KakaoAccountProfile,
    val hasEmail: Boolean,
    val emailNeedsAgreement: Boolean,
    val isEmailValid: Boolean,
    val isEmailVerified: Boolean,
    val email: String
)

@JsonNaming(value = SnakeCaseStrategy::class)
data class KakaoAccountProfile(
    val nickname: String
)