package org.info.lostark.officialschedule.query.dao

import org.info.lostark.common.dao.Dao
import org.info.lostark.officialschedule.query.dao.support.ChallengeAbyssDungeonDaoSupport
import org.info.lostark.officialschedule.query.dao.support.ContentsCalendarDaoSupport
import org.info.lostark.officialschedule.query.dao.support.RootRaidDaoSupport
import org.info.lostark.officialschedule.query.dto.ChallengeAbyssDungeonResponse
import org.info.lostark.officialschedule.query.dto.ContentsCalendarResponse
import org.info.lostark.officialschedule.query.dto.RootRaidResponse
import org.springframework.transaction.annotation.Transactional

@Dao
@Transactional(readOnly = true)
class OfficialScheduleQueryDao(
    private val challengeAbyssDungeonDaoSupport: ChallengeAbyssDungeonDaoSupport,
    private val contentsCalendarDaoSupport: ContentsCalendarDaoSupport,
    private val rootRaidDaoSupport: RootRaidDaoSupport
) {
    fun findAllChallengeAbyssDungeon(): List<ChallengeAbyssDungeonResponse> {
        return challengeAbyssDungeonDaoSupport
            .findAll()
            .map { ChallengeAbyssDungeonResponse(it) }
    }

    fun findAllContentsCalendar(): List<ContentsCalendarResponse> {
        return contentsCalendarDaoSupport
            .findAll()
            .map { ContentsCalendarResponse(it) }
    }

    fun findRootRaid(): RootRaidResponse {
        return rootRaidDaoSupport.findAll()
            .last()
            .let { RootRaidResponse(it) }
    }
}