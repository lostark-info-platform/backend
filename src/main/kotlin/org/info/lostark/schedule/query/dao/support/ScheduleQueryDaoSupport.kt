package org.info.lostark.schedule.query.dao.support

import org.info.lostark.schedule.command.domain.Schedule
import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleQueryDaoSupport : JpaRepository<Schedule, Long> {
    fun findAllByUserIdOrderByCreatedAt(userId: Long): List<Schedule>
}