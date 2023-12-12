package org.info.lostark.officialschedule.infra.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperCamelCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.info.lostark.officialschedule.command.dommain.RewardItem
import java.time.LocalDateTime


@JsonNaming(UpperCamelCaseStrategy::class)
data class RewardItemResult(
    val name: String,
    val icon: String,
    val grade: String,
    val startTimes: List<LocalDateTime>?
) {
    fun toEntity(): RewardItem {
        return RewardItem(
            name,
            icon,
            grade,
            startTimes?.joinToString(",")
        )
    }
}