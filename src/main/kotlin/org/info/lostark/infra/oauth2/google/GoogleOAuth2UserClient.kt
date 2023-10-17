package org.info.lostark.infra.oauth2.google

import org.info.lostark.domain.oauth2.OAuth2User
import org.info.lostark.domain.oauth2.OAuth2UserClient
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