package org.info.lostark.user.query.dto

import org.info.lostark.user.command.domain.User
import java.time.LocalDateTime

data class UserQueryResponse(
    val id: Long,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime
) {
    constructor(user: User) : this(
        user.id,
        user.name,
        user.email,
        user.createdAt
    )
}