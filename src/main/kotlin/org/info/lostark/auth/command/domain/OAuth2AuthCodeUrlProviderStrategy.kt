package org.info.lostark.auth.command.domain

import org.info.lostark.user.command.domain.SocialProvider

interface OAuth2AuthCodeUrlProviderStrategy {
    val support: SocialProvider
    fun provide(state: String?): String
}