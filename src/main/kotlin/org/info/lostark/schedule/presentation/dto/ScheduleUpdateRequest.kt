package org.info.lostark.schedule.presentation.dto

import java.time.LocalDateTime
import org.info.lostark.schedule.command.application.dto.ScheduleUpdateCommand

data class ScheduleUpdateRequest(
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val category: String,
    val alarmAgoMinute: Int?,
    val memo: String
) {
    fun toCommand(userId: Long, scheduleId: Long): ScheduleUpdateCommand {
        return ScheduleUpdateCommand(
            userId,
            scheduleId,
            title,
            startDate,
            endDate,
            category,
            alarmAgoMinute,
            memo
        )
    }
}
