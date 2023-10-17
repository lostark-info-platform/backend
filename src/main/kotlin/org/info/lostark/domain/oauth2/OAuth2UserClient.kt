package org.info.lostark.domain.oauth2

interface OAuth2UserClient {
    fun fetch(accessToken: String): OAuth2User
}