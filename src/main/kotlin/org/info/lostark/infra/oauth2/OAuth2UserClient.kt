package org.info.lostark.infra.oauth2

import org.info.lostark.domain.oauth2.OAuth2User

interface OAuth2UserClient {
    fun fetch(accessToken: String): OAuth2User
}