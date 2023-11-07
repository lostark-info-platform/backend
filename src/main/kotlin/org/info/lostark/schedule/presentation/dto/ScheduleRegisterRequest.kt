package org.info.lostark.schedule.presentation.dto

import java.time.LocalDateTime
import org.info.lostark.schedule.command.application.dto.ScheduleRegisterCommand

data class ScheduleRegisterRequest(
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val category: String,
    val alarmAgoMinute: Int?,
    val memo: String
) {
    fun toCommand(userId: Long): ScheduleRegisterCommand {
        return ScheduleRegisterCommand(
            userId,
            title,
            startDate,
            endDate,
            category,
            alarmAgoMinute,
            memo
        )
    }
}
