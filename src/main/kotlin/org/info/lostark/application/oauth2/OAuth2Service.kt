package org.info.lostark.application.oauth2

import org.info.lostark.application.JwtTokenResponse
import org.info.lostark.application.RefreshTokenService
import org.info.lostark.domain.oauth2.OAuth2User
import org.info.lostark.domain.oauth2.OAuth2UserRepository
import org.info.lostark.domain.oauth2.findByOAuth2Id
import org.info.lostark.domain.user.Password
import org.info.lostark.domain.user.User
import org.info.lostark.domain.user.UserInformation
import org.info.lostark.domain.user.UserRepository
import org.info.lostark.security.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OAuth2Service(
    private val oAuth2Resolver: OAuth2StrategyResolver,
    private val oAuth2UserRepository: OAuth2UserRepository,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenService: RefreshTokenService
) {
    @Transactional
    fun login(request: OAuth2LoginRequest): JwtTokenResponse {
        val fetchedOAuth2User = oAuth2Resolver
            .resolve(request.provider)
            .getOAuth2User(request.accessToken)
        val oAuth2User = oAuth2UserRepository
            .findByOAuth2Id(fetchedOAuth2User.oAuth2Id)
            ?: oAuth2UserRepository.save(createUser(fetchedOAuth2User))

        return generate(oAuth2User.user!!)
    }

    private fun createUser(oAuth2User: OAuth2User): OAuth2User {
        val savedUser = userRepository.save(
            User(
                UserInformation(oAuth2User.nickname, oAuth2User.email),
                Password(oAuth2User.oAuth2Id.providerUserId)
            )
        )
        return oAuth2User.apply { user = savedUser }
    }

    private fun generate(user: User): JwtTokenResponse {
        val token = jwtTokenProvider.createToken(user.email)
        val refreshToken = refreshTokenService.create(user.id)
        return JwtTokenResponse(token, refreshToken)
    }
}