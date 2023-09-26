package org.info.lostark.fixture

import java.util.UUID
import org.info.lostark.domain.user.Password
import org.info.lostark.domain.user.RefreshToken
import org.info.lostark.domain.user.RefreshTokenByUser
import org.info.lostark.domain.user.User

const val NAME = "홍길동"
const val EMAIL = "test@email.com"
val PASSWORD: Password = Password("password")

fun createUser(
    name: String = NAME,
    email: String = EMAIL,
    password: Password = PASSWORD,
    id: Long = 0L
): User {
    return User(name, email, password, id)
}

fun createRefreshToken(
    id: String = UUID.randomUUID().toString(),
    userId: Long = 0L
): RefreshToken {
    return RefreshToken(id, userId)
}

fun createRefreshTokenByUser(
    id: Long = 0L,
    tokens: MutableList<String> = mutableListOf(UUID.randomUUID().toString())
): RefreshTokenByUser {
    return RefreshTokenByUser(id, tokens)
}