package org.info.lostark.fixture

import org.info.lostark.domain.user.Password
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