package org.info.lostark.auth.infra.oauth2.google.client

import org.info.lostark.auth.command.domain.OAuth2UserClientStrategy
import org.info.lostark.auth.command.domain.OAuth2UserData
import org.info.lostark.auth.infra.oauth2.google.GoogleOAuth2Properties
import org.info.lostark.user.command.domain.SocialProvider
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Component
class GoogleOAuth2UserClientStrategy(
    private val googleApiClient: GoogleApiClient,
    private val properties: GoogleOAuth2Properties
) : OAuth2UserClientStrategy {
    override val support: SocialProvider
        get() = SocialProvider.GOOGLE

    override fun fetch(code: String): OAuth2UserData {
        val googleToken = googleApiClient.fetchToken(tokenRequestParams(code))
        val googleUserResponse = googleApiClient.fetchUser("Bearer ${googleToken.accessToken}")
        return googleUserResponse.toOAuth2UserData()
    }

    private fun tokenRequestParams(code: String): MultiValueMap<String, String> {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", properties.clientId)
        params.add("client_secret", properties.clientSecret)
        params.add("code", code)
        params.add("redirect_uri", properties.redirectUri)
        return params
    }
}