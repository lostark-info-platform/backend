package org.info.lostark.schedule.presentation

import org.info.lostark.common.presentation.ApiResponse
import org.info.lostark.schedule.command.application.ScheduleService
import org.info.lostark.schedule.presentation.dto.ScheduleRegisterRequest
import org.info.lostark.schedule.presentation.dto.ScheduleUpdateRequest
import org.info.lostark.schedule.query.ScheduleQueryService
import org.info.lostark.schedule.query.dto.ScheduleQueryResponse
import org.info.lostark.user.command.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/schedules")
class ScheduleRestController(
    private val scheduleQueryService: ScheduleQueryService,
    private val scheduleService: ScheduleService

) {
    @GetMapping
    fun findAllScheduleByUserId(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<ApiResponse<List<ScheduleQueryResponse>>> {
        return ResponseEntity.ok(ApiResponse.success(scheduleQueryService.findAllScheduleByUserId(user.id)))
    }

    @PostMapping
    fun register(
        @AuthenticationPrincipal user: User,
        @RequestBody request: ScheduleRegisterRequest
    ): ResponseEntity<Unit> {
        scheduleService.register(request.toCommand(user.id))
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{scheduleId}")
    fun update(
        @AuthenticationPrincipal user: User,
        @PathVariable scheduleId: Long,
        @RequestBody request: ScheduleUpdateRequest
    ): ResponseEntity<Unit> {
        scheduleService.update(request.toCommand(user.id, scheduleId))
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{scheduleId}")
    fun delete(
        @AuthenticationPrincipal user: User,
        @PathVariable scheduleId: Long,
    ): ResponseEntity<Unit> {
        scheduleService.delete(user.id, scheduleId)
        return ResponseEntity.ok().build()
    }
}