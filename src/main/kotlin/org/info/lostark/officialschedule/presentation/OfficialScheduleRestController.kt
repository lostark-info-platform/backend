package org.info.lostark.officialschedule.presentation

import org.info.lostark.common.presentation.ApiResponse
import org.info.lostark.officialschedule.command.dommain.ContentsCalendarPrimaryCategory
import org.info.lostark.officialschedule.command.dommain.ContentsCategory
import org.info.lostark.officialschedule.command.dommain.OtherContentsType
import org.info.lostark.officialschedule.query.OfficialScheduleQueryService
import org.info.lostark.officialschedule.query.dto.OfficialContentSummaryResponse
import org.info.lostark.officialschedule.query.dto.OfficialContentsDetailsQueryResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/official")
@PreAuthorize("hasRole('ROLE_BASIC')")
class OfficialScheduleRestController(
    private val officialScheduleQueryService: OfficialScheduleQueryService
) {
    @GetMapping("/summary")
    fun summary(): ResponseEntity<ApiResponse<List<OfficialContentSummaryResponse>>> {
        return ResponseEntity.ok(ApiResponse.success(officialScheduleQueryService.getSummary()))
    }

    @GetMapping("/contents")
    fun getContents(
        category: Contents,
        location: String?
    ): ResponseEntity<ApiResponse<OfficialContentsDetailsQueryResponse>> {
        return ResponseEntity.ok(ApiResponse.success(officialScheduleQueryService.getContents(category, location)))
    }

    @GetMapping("/field-boss/location")
    fun getFieldBossLocation(): ResponseEntity<ApiResponse<List<String>>> {
        return ResponseEntity.ok(ApiResponse.success(officialScheduleQueryService.getFieldBossLocation()))
    }
}

enum class Contents {
    FIELD_BOSS,
    CHAOS_GATE,
    ADVENTURE_ISLAND,
    BEGINNING_ISLAND,
    ABYSS_DUNGEON,
    GUARDIAN_RAID;

    fun toDomain(): ContentsCategory {
        return when (this) {
            FIELD_BOSS -> ContentsCalendarPrimaryCategory.FIELD_BOSS
            CHAOS_GATE -> ContentsCalendarPrimaryCategory.CHAOS_GATE
            ADVENTURE_ISLAND -> ContentsCalendarPrimaryCategory.ADVENTURE_ISLAND
            BEGINNING_ISLAND -> ContentsCalendarPrimaryCategory.BEGINNING_ISLAND
            ABYSS_DUNGEON -> OtherContentsType.ABYSS_DUNGEON
            GUARDIAN_RAID -> OtherContentsType.GUARDIAN_RAID
        }
    }

}