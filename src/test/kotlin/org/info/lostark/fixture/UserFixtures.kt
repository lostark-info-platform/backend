package org.info.lostark.fixture

import org.info.lostark.auth.command.application.dto.JwtTokenCommandResponse
import org.info.lostark.auth.command.domain.OAuth2Id
import org.info.lostark.auth.command.domain.OAuth2Provider
import org.info.lostark.auth.command.domain.OAuth2User
import org.info.lostark.user.command.domain.Password
import org.info.lostark.user.command.domain.RefreshToken
import org.info.lostark.user.command.domain.RefreshTokenByUser
import org.info.lostark.user.command.domain.User
import java.util.*

const val NAME = "홍길동"
const val EMAIL = "test@email.com"
val PASSWORD: Password = Password("password")

fun createUser(
    name: String = NAME,
    email: String = EMAIL,
    password: Password = PASSWORD,
    id: Long = 0L
): User {
    return User(name, email, password, id)
}

fun createRefreshToken(
    id: String = UUID.randomUUID().toString(),
    userId: Long = 0L
): RefreshToken {
    return RefreshToken(id, userId)
}

fun createRefreshTokenByUser(
    id: Long = 0L,
    tokens: MutableList<String> = mutableListOf(UUID.randomUUID().toString())
): RefreshTokenByUser {
    return RefreshTokenByUser(id, tokens)
}

fun createJwtResponse(): JwtTokenCommandResponse {
    return JwtTokenCommandResponse(
        "valid_token",
        "refresh_token"
    )
}

fun createOAuth2User(
    providerUserId: String = "101010101010",
    provider: OAuth2Provider = OAuth2Provider.KAKAO,
    email: String = "email@gmail.com",
    nickname: String = "nickname",
    user: User? = null
): OAuth2User {
    val oAuth2User = OAuth2User(
        oAuth2Id = OAuth2Id(providerUserId, provider),
        email = email,
        nickname = nickname
    )
    if (user != null) oAuth2User.linkUser(user)
    return oAuth2User
}