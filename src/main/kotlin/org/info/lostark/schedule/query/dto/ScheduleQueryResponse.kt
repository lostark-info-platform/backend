package org.info.lostark.schedule.query.dto

import org.info.lostark.schedule.command.domain.Schedule
import org.info.lostark.schedule.command.domain.ScheduleState
import java.time.LocalDateTime

data class ScheduleQueryResponse(
    val scheduleId: Long,
    val title: String,
    val category: String,
    val state: ScheduleState,
    val memo: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val alarmDate: LocalDateTime?,
) {
    constructor(schedule: Schedule) : this(
        scheduleId = schedule.id,
        title = schedule.title,
        category = schedule.category,
        state = schedule.state,
        memo = schedule.memo,
        startDate = schedule.startDate,
        endDate = schedule.endDate,
        alarmDate = schedule.alarmDate
    )
}