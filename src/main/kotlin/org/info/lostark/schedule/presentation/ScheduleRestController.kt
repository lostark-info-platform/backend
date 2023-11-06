package org.info.lostark.schedule.presentation

import org.info.lostark.common.presentation.ApiResponse
import org.info.lostark.common.security.LoginUser
import org.info.lostark.schedule.query.ScheduleQueryService
import org.info.lostark.schedule.query.dto.ScheduleQueryResponse
import org.info.lostark.user.command.domain.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/schedules")
class ScheduleRestController(
    private val scheduleQueryService: ScheduleQueryService
) {
    @GetMapping
    fun findAllScheduleByUserId(@LoginUser user: User): ApiResponse<List<ScheduleQueryResponse>> {
        return ApiResponse.success(scheduleQueryService.findAllScheduleByUserId(user.id))
    }
}