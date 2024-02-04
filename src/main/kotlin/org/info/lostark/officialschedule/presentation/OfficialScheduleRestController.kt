package org.info.lostark.officialschedule.presentation

import org.info.lostark.common.presentation.ApiResponse
import org.info.lostark.officialschedule.query.OfficialContent
import org.info.lostark.officialschedule.query.OfficialScheduleQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/official")
class OfficialScheduleRestController(
    private val officialScheduleQueryService: OfficialScheduleQueryService
) {
    @GetMapping("/summary")
    fun summary(): ResponseEntity<ApiResponse<List<OfficialContent>>> {
        return ResponseEntity.ok(ApiResponse.success(officialScheduleQueryService.getSummary()))
    }
}