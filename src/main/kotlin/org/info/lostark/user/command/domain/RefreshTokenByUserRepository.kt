package org.info.lostark.user.command.domain

import org.springframework.data.repository.CrudRepository

interface RefreshTokenByUserRepository : CrudRepository<RefreshTokenByUser, Long>