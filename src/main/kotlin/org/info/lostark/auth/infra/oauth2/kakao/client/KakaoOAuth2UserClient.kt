package org.info.lostark.auth.infra.oauth2.kakao.client

import org.info.lostark.auth.command.domain.OAuth2User
import org.info.lostark.auth.command.domain.OAuth2UserClient
import org.info.lostark.auth.infra.oauth2.kakao.KakaoOAuth2Properties
import org.info.lostark.auth.infra.oauth2.kakao.dto.KakaoUserResponse
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Component
class KakaoOAuth2UserClient(
    private val kakaoApiClient: KakaoApiClient,
    private val properties: KakaoOAuth2Properties
) : OAuth2UserClient {
    override fun fetch(code: String): OAuth2User {
        val kakaotoken = kakaoApiClient.fetchToken(tokenRequestParams(code))
        val kakaoUserResponse: KakaoUserResponse = kakaoApiClient.fetchUser("Bearer ${kakaotoken.accessToken}")
        return kakaoUserResponse.toEntity()
    }

    private fun tokenRequestParams(code: String): MultiValueMap<String, String> {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", properties.clientId)
        params.add("client_secret", properties.clientSecret)
        params.add("code", code)
        params.add("redirect_uri", properties.redirectUri)
        return params
    }
}