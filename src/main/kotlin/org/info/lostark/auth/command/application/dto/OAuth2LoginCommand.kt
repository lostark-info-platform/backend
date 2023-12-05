package org.info.lostark.auth.command.application.dto

import org.info.lostark.user.command.domain.SocialProvider

data class OAuth2LoginCommand(
    val socialProvider: SocialProvider,
    val code: String
)
