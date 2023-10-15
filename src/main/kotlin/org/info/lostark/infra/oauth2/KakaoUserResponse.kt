package org.info.lostark.infra.oauth2

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.info.lostark.domain.oauth2.OAuth2Id
import org.info.lostark.domain.oauth2.OAuth2Provider
import org.info.lostark.domain.oauth2.OAuth2User
import java.time.Instant

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KakaoUserResponse(
    val id: Long,
    val connectedAt: Instant,
    val properties: KakaoUserProperties,
    val kakaoAccount: KakaoAccount,
) {
    fun toEntity(): OAuth2User {
        return OAuth2User(
            OAuth2Id(id, OAuth2Provider.KAKAO),
            kakaoAccount.email,
            properties.nickname
        )
    }
}

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KakaoUserProperties(
    val nickname: String
)

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KakaoAccount(
    val profileNicknameNeedsAgreement: Boolean,
    val profile: KakaoAccountProfile,
    val hasEmail: Boolean,
    val emailNeedsAgreement: Boolean,
    val isEmailValid: Boolean,
    val isEmailVerified: Boolean,
    val email: String
)

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KakaoAccountProfile(
    val nickname: String
)