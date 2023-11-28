package org.info.lostark.auth.command.application.dto

import org.info.lostark.auth.command.domain.SocialProvider

data class OAuth2LoginCommand(
    val socialProvider: SocialProvider,
    val code: String
)
