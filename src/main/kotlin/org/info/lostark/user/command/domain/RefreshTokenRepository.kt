package org.info.lostark.user.command.domain

import org.springframework.data.jpa.repository.JpaRepository


interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {
    fun findByJwt(jwt: String): RefreshToken?
    fun deleteAllByUserId(userId: Long)
}