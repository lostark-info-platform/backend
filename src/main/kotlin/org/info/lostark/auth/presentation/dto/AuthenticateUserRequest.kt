package org.info.lostark.auth.presentation.dto

import jakarta.validation.constraints.Email
import org.info.lostark.user.command.domain.Password

data class AuthenticateUserRequest(
    @field:Email
    val email: String,
    val password: Password
)