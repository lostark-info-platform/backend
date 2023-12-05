package org.info.lostark.officialschedule.presentation

import org.info.lostark.officialschedule.query.OfficialScheduleQueryService
import org.info.lostark.officialschedule.query.dto.ChallengeAbyssDungeonResponse
import org.info.lostark.officialschedule.query.dto.ContentsCalendarResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/official")
class OfficialScheduleRestController(
    private val officialScheduleQueryService: OfficialScheduleQueryService
) {
    @GetMapping("/abyss")
    fun abyss(): List<ChallengeAbyssDungeonResponse> {
        return officialScheduleQueryService.findAllChallengeAbyssDungeon()
    }

    @GetMapping("/contents")
    fun contents(): List<ContentsCalendarResponse> {
        return officialScheduleQueryService.findAllContentsCalendar()
    }
}