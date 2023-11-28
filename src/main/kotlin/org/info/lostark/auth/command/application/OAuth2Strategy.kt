package org.info.lostark.auth.command.application

import org.info.lostark.auth.command.domain.OAuth2User
import org.info.lostark.auth.command.domain.SocialProvider

interface OAuth2Strategy {
    val provider: SocialProvider

    fun getOAuth2User(code: String): OAuth2User
}