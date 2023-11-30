package org.info.lostark.auth.command.domain

import org.info.lostark.user.command.domain.SocialProvider

interface OAuth2UserClientStrategy {
    val support: SocialProvider
    fun fetch(code: String): OAuth2UserData
}