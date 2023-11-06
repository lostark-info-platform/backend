package org.info.lostark.schedule.query.dao

import org.info.lostark.common.dao.Dao
import org.info.lostark.schedule.query.dao.support.ScheduleQueryDaoSupport
import org.info.lostark.schedule.query.dto.ScheduleQueryResponse
import org.springframework.transaction.annotation.Transactional

@Dao
@Transactional(readOnly = true)
class ScheduleQueryResponseDao(
    private val scheduleQueryDaoSupport: ScheduleQueryDaoSupport
) {
    fun findAllScheduleByUserId(userId: Long): List<ScheduleQueryResponse> {
        return scheduleQueryDaoSupport.findAllByUserIdOrderByCreatedAt(userId)
            .map { ScheduleQueryResponse(it) }
    }
}