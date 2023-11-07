package org.info.lostark.fixture

import java.time.LocalDateTime
import org.info.lostark.schedule.command.domain.Schedule
import org.info.lostark.schedule.command.domain.ScheduleState
import org.info.lostark.schedule.command.domain.ScheduleState.TODO
import org.info.lostark.schedule.presentation.dto.ScheduleRegisterRequest
import org.info.lostark.schedule.presentation.dto.ScheduleUpdateRequest
import org.info.lostark.schedule.query.dto.ScheduleQueryResponse
import org.info.lostark.user.command.domain.User

private const val SCHEDULE_TITLE = "할일 제목"
private const val SCHEDULE_CATEGORY = "장비강화"
private const val ALARM_AGO_MINUTE = 5
private const val MEMO = "길드원 이든과 함께하기"
val START_DATE: LocalDateTime = LocalDateTime.now().plusHours(1)
val END_DATE: LocalDateTime = LocalDateTime.now().plusHours(2)
val ALARM_DATE = START_DATE.minusMinutes(5)

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

fun createScheduleQueryResponse(
    scheduleId: Long = 1L,
    title: String = SCHEDULE_TITLE,
    category: String = SCHEDULE_CATEGORY,
    state: ScheduleState = TODO,
    memo: String = MEMO,
    startDate: LocalDateTime = START_DATE,
    endDate: LocalDateTime = END_DATE,
    alarmDate: LocalDateTime = ALARM_DATE
): ScheduleQueryResponse {
    return ScheduleQueryResponse(
        scheduleId,
        title,
        category,
        state,
        memo,
        startDate,
        endDate,
        alarmDate
    )
}

fun createScheduleRegisterRequest(
    title: String = SCHEDULE_TITLE,
    startDate: LocalDateTime = START_DATE,
    endDate: LocalDateTime = END_DATE,
    category: String = SCHEDULE_CATEGORY,
    alarmAgoMinute: Int? = 5,
    memo: String = MEMO
): ScheduleRegisterRequest {
    return ScheduleRegisterRequest(
        title,
        startDate,
        endDate,
        category,
        alarmAgoMinute,
        memo
    )
}

fun createScheduleUpdateRequest(
    title: String = SCHEDULE_TITLE,
    startDate: LocalDateTime = START_DATE,
    endDate: LocalDateTime = END_DATE,
    category: String = SCHEDULE_CATEGORY,
    alarmAgoMinute: Int? = 5,
    memo: String = MEMO
): ScheduleUpdateRequest {
    return ScheduleUpdateRequest(
        title,
        startDate,
        endDate,
        category,
        alarmAgoMinute,
        memo
    )
}