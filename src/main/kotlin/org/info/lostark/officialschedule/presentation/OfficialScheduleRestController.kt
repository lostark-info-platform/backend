package org.info.lostark.officialschedule.presentation

import org.info.lostark.common.presentation.ApiResponse
import org.info.lostark.officialschedule.query.OfficialScheduleQueryService
import org.info.lostark.officialschedule.query.dto.ChallengeAbyssDungeonResponse
import org.info.lostark.officialschedule.query.dto.ContentsCalendarResponse
import org.info.lostark.officialschedule.query.dto.RootRaidResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/official")
class OfficialScheduleRestController(
    private val officialScheduleQueryService: OfficialScheduleQueryService
) {
    @GetMapping("/abyss")
    fun abyss(): ApiResponse<List<ChallengeAbyssDungeonResponse>> {
        return ApiResponse.success(officialScheduleQueryService.findAllChallengeAbyssDungeon())
    }

    @GetMapping("/contents")
    fun contents(): ApiResponse<List<ContentsCalendarResponse>> {
        return ApiResponse.success(officialScheduleQueryService.findAllContentsCalendar())
    }

    @GetMapping("/raids")
    fun raids(): ApiResponse<RootRaidResponse> {
        return ApiResponse.success(officialScheduleQueryService.findRootRaid())
    }
}