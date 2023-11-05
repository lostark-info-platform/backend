package org.info.lostark.auth.command.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.info.lostark.auth.infra.oauth2.google.GoogleApiClient
import org.info.lostark.auth.infra.oauth2.google.GoogleOAuth2UserClient
import org.info.lostark.auth.infra.oauth2.google.GoogleUserResponse
import org.info.lostark.auth.infra.oauth2.kakao.*
import java.time.LocalDateTime

class OAuth2UserClientTest : StringSpec({
    "GoogleOAuth2UserClient를 호출하면 googleUserResponse를 응답한다" {
        val googleApiClient = mockk<GoogleApiClient>()
        val googleOAuth2UserClient = GoogleOAuth2UserClient(googleApiClient)
        every { googleApiClient.fetchUser(any()) } returns GoogleUserResponse(
            "provider_user_id",
            "user@gmail.com",
            "김구글"
        )
        googleOAuth2UserClient.fetch("access_token").email shouldBe "user@gmail.com"
    }
    "KakaoOAuth2UserClient를 호출하면 kakaoUserResponse를 응답한다" {
        val kakaoApiClient = mockk<KakaoApiClient>()
        val kakaoOAuth2UserClient = KakaoOAuth2UserClient(kakaoApiClient)
        every { kakaoApiClient.fetchUser(any()) } returns KakaoUserResponse(
            100L,
            LocalDateTime.now(),
            KakaoUserProperties("nickname"),
            KakaoAccount(
                profileNicknameNeedsAgreement = true,
                profile = KakaoAccountProfile(nickname = "nickname"),
                hasEmail = true,
                emailNeedsAgreement = true,
                isEmailValid = true,
                isEmailVerified = true,
                email = "user@kakao.com"
            )
        )
        kakaoOAuth2UserClient.fetch("access_token").email shouldBe "user@kakao.com"
    }
})