package org.info.lostark.schedule.command.domain

import jakarta.persistence.*
import org.info.lostark.common.domain.BaseRootEntity
import org.info.lostark.schedule.command.domain.ScheduleState.DONE
import org.info.lostark.schedule.command.domain.ScheduleState.TODO
import org.info.lostark.user.command.domain.User
import java.time.LocalDateTime

@Entity
class Schedule private constructor(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,
    id: Long = 0
) : BaseRootEntity<Schedule>(id) {
    companion object {
        fun of(
            user: User,
            title: String,
            startDate: LocalDateTime,
            endDate: LocalDateTime,
            category: String,
            alarmAgoMinute: Int?,
            memo: String
        ): Schedule {
            return Schedule(user).apply {
                update(title, startDate, endDate, category, alarmAgoMinute, memo)
            }
        }
    }

    @Column
    var title: String = ""
        protected set

    @Column
    var category: String = ""
        protected set

    @Column
    @Enumerated(EnumType.STRING)
    var state: ScheduleState = TODO
        protected set

    @Column
    var memo: String = ""
        protected set

    @Column
    var startDate: LocalDateTime = LocalDateTime.now()
        protected set

    @Column
    var endDate: LocalDateTime = LocalDateTime.now()
        protected set

    @Column
    var alarmDate: LocalDateTime? = null
        protected set

    fun update(
        title: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        category: String,
        alarmAgoMinute: Int?,
        memo: String
    ) {
        this.title = title
        this.startDate = startDate
        this.endDate = endDate
        this.category = category
        this.alarmDate = subtractAlarmMinuteFromStartDate(alarmAgoMinute, startDate)
        this.memo = memo
    }

    fun check() {
        state = DONE
    }

    fun uncheck() {
        state = TODO
    }

    private fun subtractAlarmMinuteFromStartDate(alarmAgoMinute: Int?, startDate: LocalDateTime): LocalDateTime? {
        return alarmAgoMinute?.let { startDate.minusMinutes(alarmAgoMinute.toLong()) }
    }
}
