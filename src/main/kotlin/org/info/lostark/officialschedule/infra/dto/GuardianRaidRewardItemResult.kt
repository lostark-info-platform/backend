package org.info.lostark.officialschedule.infra.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperCamelCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.info.lostark.officialschedule.command.dommain.GuardianRaidRewardItem

@JsonNaming(UpperCamelCaseStrategy::class)
data class GuardianRaidRewardItemResult(
    val expeditionItemLevel: Int,
    val items: List<RewardItemResult>
) {
    fun toEntity(): GuardianRaidRewardItem {
        return GuardianRaidRewardItem(
            expeditionItemLevel,
            items.map { it.toEntity() }.toMutableList()
        )
    }
}