package org.info.lostark.auth.presentation.dto

import org.info.lostark.auth.command.application.dto.OAuth2LoginCommand
import org.info.lostark.auth.command.domain.SocialProvider

data class OAuth2LoginRequest(
    val provider: String,
    val code: String
) {
    fun toCommand(): OAuth2LoginCommand {
        return OAuth2LoginCommand(SocialProvider.of(provider), code)
    }
}
