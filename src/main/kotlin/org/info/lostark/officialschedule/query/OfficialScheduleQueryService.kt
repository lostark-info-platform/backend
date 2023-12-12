package org.info.lostark.officialschedule.query

import org.info.lostark.officialschedule.query.dao.OfficialScheduleQueryDao
import org.info.lostark.officialschedule.query.dto.ChallengeAbyssDungeonResponse
import org.info.lostark.officialschedule.query.dto.ContentsCalendarResponse
import org.info.lostark.officialschedule.query.dto.RootRaidResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OfficialScheduleQueryService(
    private val officialScheduleQueryDao: OfficialScheduleQueryDao
) {
    fun findAllChallengeAbyssDungeon(): List<ChallengeAbyssDungeonResponse> {
        return officialScheduleQueryDao.findAllChallengeAbyssDungeon()
    }

    fun findAllContentsCalendar(): List<ContentsCalendarResponse> {
        return officialScheduleQueryDao.findAllContentsCalendar()
    }

    fun findRootRaid(): RootRaidResponse {
        return officialScheduleQueryDao.findRootRaid()
    }
}