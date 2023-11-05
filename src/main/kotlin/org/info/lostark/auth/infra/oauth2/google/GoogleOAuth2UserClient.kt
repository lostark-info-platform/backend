package org.info.lostark.auth.infra.oauth2.google

import org.info.lostark.auth.command.domain.OAuth2User
import org.info.lostark.auth.command.domain.OAuth2UserClient
import org.springframework.stereotype.Component

@Component
class GoogleOAuth2UserClient(
    private val googleApiClient: GoogleApiClient
) : OAuth2UserClient {
    override fun fetch(accessToken: String): OAuth2User {
        val googleUserResponse = googleApiClient.fetchUser("Bearer $accessToken")
        return googleUserResponse.toEntity()
    }
}