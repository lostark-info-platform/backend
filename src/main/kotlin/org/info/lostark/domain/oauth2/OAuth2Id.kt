package org.info.lostark.domain.oauth2

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class OAuth2Id(
    @Column
    val providerUserId: Long,

    @Column
    @Enumerated(value = EnumType.STRING)
    val provider: OAuth2Provider
)
