package org.info.lostark.auth.presentation.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.info.lostark.auth.command.application.dto.RegisterUserCommand
import org.info.lostark.user.command.domain.Password

data class RegisterUserRequest(
    @field:NotBlank
    val name: String,
    @field:Email
    val email: String,
    val password: Password,
    val confirmPassword: Password
) {
    fun toCommand(): RegisterUserCommand {
        return RegisterUserCommand(
            name, email, password, confirmPassword
        )
    }
}