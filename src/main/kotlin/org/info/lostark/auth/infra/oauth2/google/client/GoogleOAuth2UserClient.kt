package org.info.lostark.auth.infra.oauth2.google.client

import org.info.lostark.auth.command.domain.OAuth2User
import org.info.lostark.auth.command.domain.OAuth2UserClient
import org.info.lostark.auth.infra.oauth2.google.GoogleOAuth2Properties
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Component
class GoogleOAuth2UserClient(
    private val googleApiClient: GoogleApiClient,
    private val properties: GoogleOAuth2Properties
) : OAuth2UserClient {
    override fun fetch(code: String): OAuth2User {
        val googleToken = googleApiClient.fetchToken(tokenRequestParams(code))
        val googleUserResponse = googleApiClient.fetchUser("Bearer ${googleToken.accessToken}")
        return googleUserResponse.toEntity()
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