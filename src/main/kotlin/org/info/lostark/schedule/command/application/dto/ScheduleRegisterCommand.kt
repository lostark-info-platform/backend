package org.info.lostark.schedule.command.application.dto

import java.time.LocalDateTime

data class ScheduleRegisterCommand(
    val userId: Long,
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val category: String,
    val alarmAgoMinute: Int?,
    val memo: String
)