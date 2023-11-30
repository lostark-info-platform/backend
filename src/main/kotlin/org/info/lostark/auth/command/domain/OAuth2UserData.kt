package org.info.lostark.auth.command.domain

import org.info.lostark.user.command.domain.SocialProvider

data class OAuth2UserData(
    val socialProvider: SocialProvider,
    val socialUid: String
)