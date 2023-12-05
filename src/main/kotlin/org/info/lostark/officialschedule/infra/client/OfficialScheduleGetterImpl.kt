package org.info.lostark.officialschedule.infra.client

import org.info.lostark.officialschedule.command.dommain.ChallengeAbyssDungeon
import org.info.lostark.officialschedule.command.dommain.ContentsCalendar
import org.info.lostark.officialschedule.command.dommain.OfficialScheduleGetter
import org.springframework.stereotype.Component

@Component
class OfficialScheduleGetterImpl(
    private val officialScheduleClient: OfficialScheduleClient
) : OfficialScheduleGetter {
    override fun fetchAllChallengeAbyssDungeon(accessToken: String): List<ChallengeAbyssDungeon> {
        return officialScheduleClient
            .fetchAllChallengeAbyssDungeon(bearer(accessToken))
            .map { it.toEntity() }
    }

    override fun fetchAllContentsCalendar(accessToken: String): List<ContentsCalendar> {
        return officialScheduleClient
            .fetchAllContentsCalendar(bearer(accessToken))
            .map { it.toEntity() }
    }

    private fun bearer(accessToken: String) = "Bearer $accessToken"
}