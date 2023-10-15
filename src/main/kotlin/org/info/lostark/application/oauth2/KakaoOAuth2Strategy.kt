package org.info.lostark.application.oauth2

import org.info.lostark.domain.oauth2.OAuth2Provider
import org.info.lostark.domain.oauth2.OAuth2User
import org.info.lostark.infra.oauth2.KakaoOAuth2UserClient
import org.springframework.stereotype.Component

@Component
class KakaoOAuth2Strategy(
    private val kakaoOAuth2UserClient: KakaoOAuth2UserClient
) : OAuth2Strategy {
    override val provider: OAuth2Provider
        get() = OAuth2Provider.KAKAO

    override fun getOAuth2User(accessToken: String): OAuth2User {
        return kakaoOAuth2UserClient.fetch(accessToken)
    }
}