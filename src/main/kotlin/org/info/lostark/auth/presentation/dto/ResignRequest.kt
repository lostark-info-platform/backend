package org.info.lostark.auth.presentation.dto

import org.info.lostark.user.command.domain.Password

data class ResignRequest(
    val password: Password
)