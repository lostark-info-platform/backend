package org.info.lostark.officialschedule.infra.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperCamelCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.info.lostark.common.util.convertStringToDateTime
import org.info.lostark.officialschedule.command.dommain.ChallengeAbyssDungeon

@JsonNaming(UpperCamelCaseStrategy::class)
data class ChallengeAbyssDungeonResult(
    val name: String,
    val description: String,
    val minCharacterLevel: Int,
    val minItemLevel: Int,
    val areaName: String,
    val startTime: String,
    val endTime: String,
    val image: String,
    val rewardItems: List<RewardItemResult>,
) {
    fun toEntity(): ChallengeAbyssDungeon {
        return ChallengeAbyssDungeon(
            name,
            description,
            minCharacterLevel,
            minItemLevel,
            areaName,
            convertStringToDateTime(startTime),
            convertStringToDateTime(endTime),
            image,
            rewardItems.map { it.toEntity() }.toMutableList()
        )
    }
}