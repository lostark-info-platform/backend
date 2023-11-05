package org.info.lostark.fixture

import org.info.lostark.schedule.command.domain.Schedule
import org.info.lostark.user.command.domain.User
import java.time.LocalDateTime

private const val SCHEDULE_TITLE = "할일 제목"
private const val SCHEDULE_CATEGORY = "장비강화"
private const val ALARM_AGO_MINUTE = 5
private const val MEMO = "길드원 이든과 함께하기"
val START_DATE: LocalDateTime = LocalDateTime.now().plusHours(1)
val END_DATE: LocalDateTime = LocalDateTime.now().plusHours(2)

fun createSchedule(
    user: User = createUser(),
    title: String = SCHEDULE_TITLE,
    startDate: LocalDateTime = START_DATE,
    endDate: LocalDateTime = END_DATE,
    category: String = SCHEDULE_CATEGORY,
    alarmAgoMinute: Int = ALARM_AGO_MINUTE,
    memo: String = MEMO
): Schedule {
    return Schedule.of(
        user, title, startDate, endDate, category, alarmAgoMinute, memo
    )
}