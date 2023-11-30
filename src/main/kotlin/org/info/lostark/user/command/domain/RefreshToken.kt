package org.info.lostark.user.command.domain

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
class RefreshToken(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Column
    val jwt: String
) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L
}