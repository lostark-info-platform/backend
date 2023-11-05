package org.info.lostark.auth.command.application

import org.info.lostark.auth.command.domain.OAuth2Provider.GOOGLE
import org.info.lostark.auth.command.domain.OAuth2User
import org.info.lostark.auth.infra.oauth2.google.GoogleOAuth2UserClient
import org.springframework.stereotype.Component

@Component
class GoogleOAuth2Strategy(
    private val googleOAuth2UserClient: GoogleOAuth2UserClient
) : OAuth2Strategy {
    override val provider = GOOGLE

    override fun getOAuth2User(accessToken: String): OAuth2User {
        return googleOAuth2UserClient.fetch(accessToken)
    }
}