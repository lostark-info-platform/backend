package org.info.lostark.auth.command.application.dto

import org.info.lostark.user.command.domain.Password
import org.info.lostark.user.command.domain.User

data class RegisterUserCommand(
    val name: String,
    val email: String,
    val password: Password,
    val confirmPassword: Password
) {
    fun toEntity(): User {
        return User(name, email, password)
    }
}
