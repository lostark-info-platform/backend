package org.info.lostark.domain.user

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun RefreshTokenRepository.getOrThrow(id: String) = findByIdOrNull(id)
    ?: throw NoSuchElementException("리프레시 토큰이 존재하지 않습니다. id: $id")

interface RefreshTokenRepository : CrudRepository<RefreshToken, String>