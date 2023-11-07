package org.info.lostark.schedule.presentation

import org.info.lostark.common.presentation.ApiResponse
import org.info.lostark.common.security.LoginUser
import org.info.lostark.schedule.command.application.ScheduleService
import org.info.lostark.schedule.presentation.dto.ScheduleRegisterRequest
import org.info.lostark.schedule.presentation.dto.ScheduleUpdateRequest
import org.info.lostark.schedule.query.ScheduleQueryService
import org.info.lostark.schedule.query.dto.ScheduleQueryResponse
import org.info.lostark.user.command.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/schedules")
class ScheduleRestController(
    private val scheduleQueryService: ScheduleQueryService,
    private val scheduleService: ScheduleService

) {
    @GetMapping
    fun findAllScheduleByUserId(@LoginUser user: User): ResponseEntity<ApiResponse<List<ScheduleQueryResponse>>> {
        return ResponseEntity.ok(ApiResponse.success(scheduleQueryService.findAllScheduleByUserId(user.id)))
    }

    @PostMapping
    fun register(@LoginUser user: User, @RequestBody request: ScheduleRegisterRequest): ResponseEntity<Unit> {
        scheduleService.register(request.toCommand(user.id))
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{scheduleId}")
    fun update(
        @LoginUser user: User,
        @PathVariable scheduleId: Long,
        @RequestBody request: ScheduleUpdateRequest
    ): ResponseEntity<Unit> {
        scheduleService.update(request.toCommand(user.id, scheduleId))
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{scheduleId}")
    fun delete(
        @LoginUser user: User,
        @PathVariable scheduleId: Long,
    ): ResponseEntity<Unit> {
        scheduleService.delete(user.id, scheduleId)
        return ResponseEntity.ok().build()
    }
}