package org.info.lostark.schedule.query

import org.info.lostark.schedule.query.dao.ScheduleQueryDao
import org.info.lostark.schedule.query.dto.ScheduleQueryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ScheduleQueryService(
    private val scheduleQueryDao: ScheduleQueryDao
) {
    fun findAllScheduleByUserId(userId: Long): List<ScheduleQueryResponse> {
        return scheduleQueryDao.findAllScheduleByUserId(userId)
    }
}