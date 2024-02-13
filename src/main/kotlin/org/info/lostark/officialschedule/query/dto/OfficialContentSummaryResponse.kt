package org.info.lostark.officialschedule.query.dto

import java.time.LocalDateTime


data class OfficialContentSummaryResponse(
    val iconUrl: String,
    val name: String,
    val startedAt: LocalDateTime?,
)

