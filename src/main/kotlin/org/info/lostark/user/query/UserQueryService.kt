package org.info.lostark.user.query

import org.info.lostark.user.command.domain.User
import org.info.lostark.user.command.domain.UserRepository
import org.info.lostark.user.command.domain.findByEmail
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val userRepository: UserRepository
) {
    fun getByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw IllegalArgumentException("회원이 존재하지 않습니다. email: $email")
    }
}