package org.info.lostark.schedule.command.domain

import org.info.lostark.user.command.domain.User
import org.springframework.data.jpa.repository.JpaRepository


interface ScheduleRepository : JpaRepository<Schedule, Long> {
    fun findAllByUser(user: User): List<Schedule>
}