package org.info.lostark.auth.command.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.info.lostark.auth.infra.oauth2.google.GoogleOAuth2Properties
import org.info.lostark.auth.infra.oauth2.google.client.GoogleApiClient
import org.info.lostark.auth.infra.oauth2.google.client.GoogleOAuth2UserClient
import org.info.lostark.auth.infra.oauth2.google.dto.GoogleToken
import org.info.lostark.auth.infra.oauth2.google.dto.GoogleUserResponse
import org.info.lostark.auth.infra.oauth2.kakao.*
import org.info.lostark.auth.infra.oauth2.kakao.client.KakaoApiClient
import org.info.lostark.auth.infra.oauth2.kakao.client.KakaoOAuth2UserClient
import org.info.lostark.auth.infra.oauth2.kakao.dto.*
import java.time.LocalDateTime

class OAuth2UserClientTest : StringSpec({
    "GoogleOAuth2UserClient를 호출하면 googleUserResponse를 응답한다" {
        val googleApiClient = mockk<GoogleApiClient>()
        val properties = GoogleOAuth2Properties("cliend_id", "client_secret", "redirect_uri", "scope")
        val googleOAuth2UserClient = GoogleOAuth2UserClient(googleApiClient, properties)

        every { googleApiClient.fetchToken(any()) } returns GoogleToken(
            "access_token",
            10000,
            "scope",
            "Bearer"
        )
        every { googleApiClient.fetchUser(any()) } returns GoogleUserResponse(
            "provider_user_id",
            "user@gmail.com",
            "김구글"
        )
        googleOAuth2UserClient.fetch("access_token").email shouldBe "user@gmail.com"
    }
    "KakaoOAuth2UserClient를 호출하면 kakaoUserResponse를 응답한다" {
        val kakaoApiClient = mockk<KakaoApiClient>()
        val properties = KakaoOAuth2Properties("cliend_id", "client_secret", "redirect_uri", "scope")
        val kakaoOAuth2UserClient = KakaoOAuth2UserClient(kakaoApiClient, properties)

        every { kakaoApiClient.fetchToken(any()) } returns KakaoToken(
            "Bearer",
            "access_token",
            "20000",
            "refresh_token",
            "10000",
            "scope"
        )
        every { kakaoApiClient.fetchUser(any()) } returns KakaoUserResponse(
            100L,
            LocalDateTime.now(),
            KakaoUserProperties("nickname"),
            KakaoAccount(
                profileNicknameNeedsAgreement = true,
                profile = KakaoAccountProfile(nickname = "nickname"),
            )
        )
        kakaoOAuth2UserClient.fetch("access_token").oAuth2Id.providerUserId shouldBe "100"
    }
})