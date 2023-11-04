package org.info.lostark.domain.schedule

import jakarta.persistence.*
import org.info.lostark.domain.common.BaseRootEntity
import org.info.lostark.domain.user.User
import java.time.LocalDateTime

@Entity
class Schedule private constructor(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user: User,

    @Column
    val title: String,

    @Column
    val startDate: LocalDateTime,

    @Column
    val endDate: LocalDateTime,

    @Column
    val category: String,

    @Column
    val alarmDate: LocalDateTime,

    id: Long = 0
) : BaseRootEntity<Schedule>(id) {
    constructor(
        user: User,
        title: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        category: String,
        alarmAgoMinute: Int
    ) : this(
        user, title, startDate, endDate, category, startDate.minusMinutes(alarmAgoMinute.toLong())
    )
}
