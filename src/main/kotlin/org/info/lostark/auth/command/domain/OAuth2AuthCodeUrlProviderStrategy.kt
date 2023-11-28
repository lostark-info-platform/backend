package org.info.lostark.auth.command.domain

interface OAuth2AuthCodeUrlProviderStrategy {
    val support: SocialProvider
    fun provide(state: String?): String
}