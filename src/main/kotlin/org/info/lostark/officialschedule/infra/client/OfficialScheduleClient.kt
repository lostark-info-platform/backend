package org.info.lostark.officialschedule.infra.client

import org.info.lostark.officialschedule.infra.dto.ChallengeAbyssDungeonResult
import org.info.lostark.officialschedule.infra.dto.ContentsCalendarResult
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange


interface OfficialScheduleClient {
    @GetExchange("https://developer-lostark.game.onstove.com/gamecontents/challenge-abyss-dungeons")
    fun fetchAllChallengeAbyssDungeon(@RequestHeader(name = "authorization") accessToken: String): List<ChallengeAbyssDungeonResult>

    @GetExchange("https://developer-lostark.game.onstove.com/gamecontents/calendar")
    fun fetchAllContentsCalendar(@RequestHeader(name = "authorization") accessToken: String): List<ContentsCalendarResult>
}