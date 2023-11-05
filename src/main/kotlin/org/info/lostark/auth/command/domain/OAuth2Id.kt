package org.info.lostark.auth.command.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class OAuth2Id(
    @Column
    val providerUserId: String,

    @Column
    @Enumerated(value = EnumType.STRING)
    val provider: OAuth2Provider
)
