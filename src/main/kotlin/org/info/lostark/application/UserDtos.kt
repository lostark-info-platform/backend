package org.info.lostark.application

import org.info.lostark.domain.user.Password
import org.info.lostark.domain.user.User

data class RegisterUserRequest(
    val name: String,
    val email: String,
    val password: Password,
    val confirmPassword: Password
) {
    fun toEntity(): User {
        return User(name, email, password)
    }
}

data class AuthenticateUserRequest(
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
) {
    constructor(user: User) : this(
        user.id,
        user.name,
        user.email
    )
}