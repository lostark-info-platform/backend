package org.info.lostark.domain.schedule

import org.info.lostark.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository


interface ScheduleRepository: JpaRepository<Schedule, Long> {
    fun findAllByUser(user: User): List<Schedule>
}