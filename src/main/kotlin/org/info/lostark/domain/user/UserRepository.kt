package org.info.lostark.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun UserRepository.findByEmail(email: String): User? = findByInformationEmail(email)
fun UserRepository.getOrThrow(id: Long): User = findByIdOrNull(id)
    ?: throw NoSuchElementException("회원이 존재하지 않습니다. id: $id")

interface UserRepository : JpaRepository<User, Long> {
    fun findByInformationEmail(email: String): User?
}