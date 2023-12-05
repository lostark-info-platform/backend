package org.info.lostark.user.query.dto

import org.info.lostark.user.command.domain.User
import java.time.LocalDateTime

data class UserQueryResponse(
    val id: Long,
    val createdAt: LocalDateTime
) {
    constructor(user: User) : this(
        user.id,
        user.createdAt
    )
}