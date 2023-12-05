package org.info.lostark.officialschedule.presentation

import org.info.lostark.common.presentation.ApiResponse
import org.info.lostark.officialschedule.command.application.OfficialScheduleService
import org.info.lostark.officialschedule.presentation.dto.OfficialScheduleStoreRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/official")
class OfficialScheduleDataController(
    private val officialScheduleService: OfficialScheduleService,
) {
    @PostMapping("/abyss")
    fun storeChallengeAbyssDungeon(@RequestBody request: OfficialScheduleStoreRequest): ApiResponse<Any> {
        officialScheduleService.storeChallengeAbyssDungeon(request.accessToken)
        return ApiResponse.success(null)
    }

    @PostMapping("/contents")
    fun contents(@RequestBody request: OfficialScheduleStoreRequest): ApiResponse<Any> {
        officialScheduleService.storeContentsCalendar(request.accessToken)
        return ApiResponse.success(null)
    }
}