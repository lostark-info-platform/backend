package org.info.lostark.application.user

import org.info.lostark.domain.user.User
import org.info.lostark.domain.user.UserRepository
import org.info.lostark.domain.user.findByEmail
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw IllegalArgumentException("회원이 존재하지 않습니다. email: $email")
    }
}