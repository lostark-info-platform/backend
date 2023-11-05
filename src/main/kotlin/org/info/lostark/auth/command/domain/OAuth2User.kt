package org.info.lostark.auth.command.domain

import jakarta.persistence.*
import org.info.lostark.common.domain.BaseEntity
import org.info.lostark.user.command.domain.User

@Entity
class OAuth2User(
    @Embedded
    val oAuth2Id: OAuth2Id,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column
    val nickname: String,

    id: Long = 0L
) : BaseEntity(id) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    var user: User? = null
        protected set

    fun linkUser(user: User) {
        this.user = user
    }
}
