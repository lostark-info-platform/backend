package org.info.lostark.auth.command.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

fun OAuth2UserRepository.findByOAuth2Id(oAuth2Id: OAuth2Id) =
    findByProviderUserIdAndProvider(oAuth2Id.providerUserId, oAuth2Id.provider)

interface OAuth2UserRepository : JpaRepository<OAuth2User, Long> {
    @Query("select o from OAuth2User o where o.oAuth2Id.providerUserId = :providerUserId and o.oAuth2Id.provider = :provider")
    fun findByProviderUserIdAndProvider(
        @Param("providerUserId") providerUserId: String,
        @Param("provider") provider: OAuth2Provider
    ): OAuth2User?
}