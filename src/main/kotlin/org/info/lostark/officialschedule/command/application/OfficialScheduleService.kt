package org.info.lostark.officialschedule.command.application

import org.info.lostark.officialschedule.command.dommain.ChallengeAbyssDungeonRepository
import org.info.lostark.officialschedule.command.dommain.ContentsCalendarRepository
import org.info.lostark.officialschedule.command.dommain.OfficialScheduleGetter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OfficialScheduleService(
    private val officialScheduleGetter: OfficialScheduleGetter,
    private val challengeAbyssDungeonRepository: ChallengeAbyssDungeonRepository,
    private val contentsCalendarRepository: ContentsCalendarRepository
) {
    fun storeChallengeAbyssDungeon(accessToken: String) {
        // TODO: findAll 말고 1개의 데이터만 찾기
        val challengeAbyssDungeons = challengeAbyssDungeonRepository.findAll()
        if (challengeAbyssDungeons.isNotEmpty()) {
            return
        }
        challengeAbyssDungeonRepository.saveAll(
            officialScheduleGetter.fetchAllChallengeAbyssDungeon(accessToken)
        )
    }

    fun storeContentsCalendar(accessToken: String) {
        // TODO: findAll 말고 1개의 데이터만 찾기
        val contentsCalendars = contentsCalendarRepository.findAll()
        if (contentsCalendars.isNotEmpty()) {
            return
        }
        contentsCalendarRepository.saveAll(
            officialScheduleGetter.fetchAllContentsCalendar(accessToken)
        )
    }
}