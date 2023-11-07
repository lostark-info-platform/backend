package org.info.lostark.schedule.query

import org.info.lostark.schedule.query.dao.ScheduleQueryResponseDao
import org.info.lostark.schedule.query.dto.ScheduleQueryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ScheduleQueryService(
    private val scheduleQueryResponseDao: ScheduleQueryResponseDao
) {
    fun findAllScheduleByUserId(userId: Long): List<ScheduleQueryResponse> {
        return scheduleQueryResponseDao.findAllScheduleByUserId(userId)
    }
}