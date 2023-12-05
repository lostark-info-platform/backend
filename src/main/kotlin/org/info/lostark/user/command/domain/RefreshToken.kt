package org.info.lostark.user.command.domain

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "refresh_token")
class RefreshToken(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user: User,

    @Column
    val jwt: String
) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L
}