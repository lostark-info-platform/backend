package org.info.lostark.schedule.command.application

import org.info.lostark.schedule.command.domain.ScheduleRepository
import org.info.lostark.schedule.command.domain.getOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ScheduleCheckService(
    private val scheduleRepository: ScheduleRepository
) {
    fun check(userId: Long, scheduleId: Long) {
        val schedule = scheduleRepository.getOrThrow(scheduleId)
        check(schedule.user.id == userId)
        schedule.check()
    }

    fun uncheck(userId: Long, scheduleId: Long) {
        val schedule = scheduleRepository.getOrThrow(scheduleId)
        check(schedule.user.id == userId)
        schedule.uncheck()
    }
}