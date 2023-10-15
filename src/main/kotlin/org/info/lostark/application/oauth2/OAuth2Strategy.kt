package org.info.lostark.application.oauth2

import org.info.lostark.domain.oauth2.OAuth2Provider
import org.info.lostark.domain.oauth2.OAuth2User

interface OAuth2Strategy {
    val provider: OAuth2Provider

    fun getOAuth2User(accessToken: String): OAuth2User
}