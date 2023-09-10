package org.info.lostark.domain.user

import org.springframework.data.repository.CrudRepository

interface RefreshTokenByUserRepository : CrudRepository<RefreshTokenByUser, Long>