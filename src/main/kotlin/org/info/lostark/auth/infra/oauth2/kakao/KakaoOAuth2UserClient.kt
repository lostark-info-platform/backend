package org.info.lostark.auth.infra.oauth2.kakao

import org.info.lostark.auth.command.domain.OAuth2User
import org.info.lostark.auth.command.domain.OAuth2UserClient
import org.springframework.stereotype.Component

@Component
class KakaoOAuth2UserClient(
    private val kakaoApiClient: KakaoApiClient
) : OAuth2UserClient {
    override fun fetch(accessToken: String): OAuth2User {
        val kakaoUserResponse: KakaoUserResponse = kakaoApiClient.fetchUser("Bearer $accessToken")
        println(kakaoUserResponse)
        return kakaoUserResponse.toEntity()
    }
}