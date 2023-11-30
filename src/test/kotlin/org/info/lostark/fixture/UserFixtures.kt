package org.info.lostark.fixture

import org.info.lostark.auth.command.application.dto.TokenResponse
import org.info.lostark.user.command.domain.RefreshToken
import org.info.lostark.user.command.domain.SocialProvider
import org.info.lostark.user.command.domain.User

const val SOCIAL_UID = "social_uid"
val SOCIAL_PROVIDER = SocialProvider.GOOGLE
const val JWT = "valid_jwt"

fun createUser(
    socialUid: String = SOCIAL_UID,
    socialProvider: SocialProvider = SOCIAL_PROVIDER,
    id: Long = 0L
): User {
    return User(socialUid, socialProvider, id)
}

fun createRefreshToken(
    user: User,
    jwt: String = JWT
): RefreshToken {
    return RefreshToken(user, jwt)
}

fun createJwtResponse(): TokenResponse {
    return TokenResponse(
        "access_token",
        "refresh_token"
    )
}
