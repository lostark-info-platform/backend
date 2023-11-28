package org.info.lostark.auth.command.application

import org.info.lostark.auth.command.domain.OAuth2User
import org.info.lostark.auth.command.domain.SocialProvider.KAKAO
import org.info.lostark.auth.infra.oauth2.kakao.client.KakaoOAuth2UserClient
import org.springframework.stereotype.Component

@Component
class KakaoOAuth2Strategy(
    private val kakaoOAuth2UserClient: KakaoOAuth2UserClient
) : OAuth2Strategy {
    override val provider = KAKAO

    override fun getOAuth2User(code: String): OAuth2User {
        return kakaoOAuth2UserClient.fetch(code)
    }
}