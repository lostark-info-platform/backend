package org.info.lostark.schedule.command.domain

import org.info.lostark.user.command.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun ScheduleRepository.getOrThrow(id: Long): Schedule = findByIdOrNull(id)
    ?: throw NoSuchElementException("스케줄이 존재하지 않습니다. id: $id")

interface ScheduleRepository : JpaRepository<Schedule, Long> {
    fun findAllByUser(user: User): List<Schedule>
}