package org.info.lostark.officialschedule.infra.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperCamelCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.info.lostark.officialschedule.command.dommain.RootRaid

@JsonNaming(UpperCamelCaseStrategy::class)
data class ChallengeGuardianRaids(
    val raids: List<GuardianRaidResult>,
    val rewardItems: List<GuardianRaidRewardItemResult>
) {
    fun toEntity(): RootRaid {
        return RootRaid(
            raids.map { it.toEntity() }.toMutableList(),
            rewardItems.map { it.toEntity() }.toMutableList()
        )
    }
}



