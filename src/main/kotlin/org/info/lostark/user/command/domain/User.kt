package org.info.lostark.user.command.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.info.lostark.common.domain.BaseRootEntity
import org.info.lostark.user.command.domain.UserState.VALID
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity
@Table(name = "user")
class User(
    @Column
    val socialUid: String,

    @Column
    @Enumerated(STRING)
    val socialProvider: SocialProvider,
    id: Long = 0L
) : BaseRootEntity<User>(id) {

    @Column
    @Enumerated(STRING)
    val state: UserState = VALID

    @Column
    @Enumerated(STRING)
    val role: Role = Role.BASIC

    val authorities: Collection<GrantedAuthority>
        get() = listOf(SimpleGrantedAuthority("ROLE_${role.name}"))

    fun resign() {
        // TODO: 정보 제거
    }
}