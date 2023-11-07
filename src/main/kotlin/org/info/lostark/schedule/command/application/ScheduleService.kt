package org.info.lostark.schedule.command.application

import org.info.lostark.schedule.command.application.dto.ScheduleRegisterCommand
import org.info.lostark.schedule.command.application.dto.ScheduleUpdateCommand
import org.info.lostark.schedule.command.domain.Schedule
import org.info.lostark.schedule.command.domain.ScheduleRepository
import org.info.lostark.schedule.command.domain.getOrThrow
import org.info.lostark.user.command.domain.UserRepository
import org.info.lostark.user.command.domain.getOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ScheduleService(
    private val userRepository: UserRepository,
    private val scheduleRepository: ScheduleRepository
) {
    fun register(command: ScheduleRegisterCommand) {
        val user = userRepository.getOrThrow(command.userId)
        val schedule = Schedule.of(
            user,
            command.title,
            command.startDate,
            command.endDate,
            command.category,
            command.alarmAgoMinute,
            command.memo
        )
        scheduleRepository.save(schedule)
    }

    fun update(command: ScheduleUpdateCommand) {
        val user = userRepository.getOrThrow(command.userId)
        val schedule = scheduleRepository.getOrThrow(command.scheduleId)
        check(schedule.user == user)
        schedule.update(
            command.title,
            command.startDate,
            command.endDate,
            command.category,
            command.alarmAgoMinute,
            command.memo
        )
    }

    fun delete(userId: Long, scheduleId: Long) {
        val user = userRepository.getOrThrow(userId)
        val schedule = scheduleRepository.getOrThrow(scheduleId)
        check(schedule.user == user)
        scheduleRepository.delete(schedule)
    }
}