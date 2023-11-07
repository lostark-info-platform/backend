package org.info.lostark.schedule.presentation

import org.info.lostark.common.security.LoginUser
import org.info.lostark.schedule.command.application.ScheduleCheckService
import org.info.lostark.user.command.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/schedules")
class ScheduleCheckRestController(
    private val scheduleCheckService: ScheduleCheckService
) {
    @PostMapping("/{scheduleId}/check")
    fun checkSchedule(
        @LoginUser user: User,
        @PathVariable scheduleId: Long,
    ): ResponseEntity<Unit> {
        scheduleCheckService.check(user.id, scheduleId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{scheduleId}/uncheck")
    fun uncheckSchedule(
        @LoginUser user: User,
        @PathVariable scheduleId: Long,
    ): ResponseEntity<Unit> {
        scheduleCheckService.uncheck(user.id, scheduleId)
        return ResponseEntity.ok().build()
    }
}