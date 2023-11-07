package org.info.lostark.schedule.command.application.dto

import java.time.LocalDateTime

data class ScheduleUpdateCommand(
    val userId: Long,
    val scheduleId: Long,
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val category: String,
    val alarmAgoMinute: Int?,
    val memo: String
)
