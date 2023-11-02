package org.info.lostark.application.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime
import org.info.lostark.domain.user.Password
import org.info.lostark.domain.user.User

data class RegisterUserRequest(
    @field:NotBlank
    val name: String,
    @field:Email
    val email: String,
    val password: Password,
    val confirmPassword: Password
) {
    fun toEntity(): User {
        return User(name, email, password)
    }
}

data class AuthenticateUserRequest(
    @field:Email
    val email: String,
    val password: Password
)

data class LogoutRequest(
    val refreshToken: String
)

data class ResignRequest(
    val password: Password
)

data class TokenRefreshRequest(
    val refreshToken: String
)

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime
) {
    constructor(user: User) : this(
        user.id,
        user.name,
        user.email,
        user.createdAt
    )
}