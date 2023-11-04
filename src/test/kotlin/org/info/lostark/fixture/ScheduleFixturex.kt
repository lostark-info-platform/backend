package org.info.lostark.fixture

import org.info.lostark.domain.schedule.Schedule
import org.info.lostark.domain.user.User
import java.time.LocalDateTime

private const val SCHEDULE_TITLE = "할일 제목"
private const val SCHEDULE_CATEGORY = "장비강화"
private const val ALARM_AGO_MINUTE = 5
val START_DATE: LocalDateTime = LocalDateTime.now().plusHours(1)
val END_DATE: LocalDateTime = LocalDateTime.now().plusHours(2)

fun createSchedule(
    user: User = createUser(),
    title: String = SCHEDULE_TITLE,
    startDate: LocalDateTime = START_DATE,
    endDate: LocalDateTime = END_DATE,
    category: String = SCHEDULE_CATEGORY,
    alarmAgoMinute: Int = ALARM_AGO_MINUTE
): Schedule {
    return Schedule(
        user, title, startDate, endDate, category, alarmAgoMinute
    )
}