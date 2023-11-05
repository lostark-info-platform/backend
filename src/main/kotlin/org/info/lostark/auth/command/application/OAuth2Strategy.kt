package org.info.lostark.auth.command.application

import org.info.lostark.auth.command.domain.OAuth2Provider
import org.info.lostark.auth.command.domain.OAuth2User

interface OAuth2Strategy {
    val provider: OAuth2Provider

    fun getOAuth2User(accessToken: String): OAuth2User
}