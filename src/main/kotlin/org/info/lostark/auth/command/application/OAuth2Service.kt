package org.info.lostark.auth.command.application

import org.info.lostark.auth.command.application.dto.JwtTokenCommandResponse
import org.info.lostark.auth.command.domain.OAuth2User
import org.info.lostark.auth.command.domain.OAuth2UserRepository
import org.info.lostark.auth.command.domain.findByOAuth2Id
import org.info.lostark.common.security.JwtTokenProvider
import org.info.lostark.user.command.domain.Password
import org.info.lostark.user.command.domain.User
import org.info.lostark.user.command.domain.UserInformation
import org.info.lostark.user.command.domain.UserRepository
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
    fun login(provider: String, accessToken: String): JwtTokenCommandResponse {
        val fetchedOAuth2User = oAuth2Resolver
            .resolve(provider)
            .getOAuth2User(accessToken)
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
        oAuth2User.linkUser(savedUser)
        return oAuth2User
    }

    private fun generate(user: User): JwtTokenCommandResponse {
        val token = jwtTokenProvider.createToken(user.email)
        val refreshToken = refreshTokenService.create(user.id)
        return JwtTokenCommandResponse(token, refreshToken)
    }
}