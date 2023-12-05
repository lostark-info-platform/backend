package org.info.lostark.officialschedule.infra.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperCamelCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.info.lostark.officialschedule.command.dommain.ContentsCalendar
import java.time.LocalDateTime

@JsonNaming(UpperCamelCaseStrategy::class)
data class ContentsCalendarResult(
    val categoryName: String,
    val contentsName: String,
    val contentsIcon: String,
    val minItemLevel: Int,
    val startTimes: List<LocalDateTime>,
    val location: String,
    val rewardItems: List<RewardItemResult>
) {
    fun toEntity(): ContentsCalendar {
        return ContentsCalendar(
            categoryName,
            contentsName,
            contentsIcon,
            minItemLevel,
            startTimes.joinToString(","),
            location,
            rewardItems.map { it.toEntity() }.toMutableList()
        )
    }
}