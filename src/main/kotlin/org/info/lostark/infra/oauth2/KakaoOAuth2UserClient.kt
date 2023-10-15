package org.info.lostark.infra.oauth2

import org.info.lostark.domain.oauth2.OAuth2User
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