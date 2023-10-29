package org.info.lostark.domain.oauth2

import jakarta.persistence.*
import org.info.lostark.domain.BaseEntity
import org.info.lostark.domain.user.User

@Entity
class OAuth2User(
    @Embedded
    val oAuth2Id: OAuth2Id,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column
    val nickname: String,

    id: Long = 0L
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    var user: User? = null
}
