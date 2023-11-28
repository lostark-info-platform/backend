package org.info.lostark.auth.infra.oauth2.google.authcode

import org.info.lostark.auth.command.domain.OAuth2AuthCodeUrlProviderStrategy
import org.info.lostark.auth.command.domain.SocialProvider
import org.info.lostark.auth.infra.oauth2.google.GoogleOAuth2Properties
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class GoogleAuthCodeRedirectUriProvider(
    private val properties: GoogleOAuth2Properties
) : OAuth2AuthCodeUrlProviderStrategy {
    override val support: SocialProvider
        get() = SocialProvider.GOOGLE

    override fun provide(state: String?): String {
        return UriComponentsBuilder
            .fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
            .queryParam("response_type", "code")
            .queryParam("client_id", properties.clientId)
            .queryParam("redirect_uri", properties.redirectUri)
            .queryParam("scope", properties.scope)
            .queryParam("state", state)
            .build()
            .toUriString()
    }
}