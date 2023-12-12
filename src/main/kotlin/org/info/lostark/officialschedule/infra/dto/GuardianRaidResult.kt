package org.info.lostark.officialschedule.infra.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperCamelCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.info.lostark.officialschedule.command.dommain.GuardianRaid

@JsonNaming(UpperCamelCaseStrategy::class)
data class GuardianRaidResult(
    val name: String,
    val description: String,
    val minCharacterLevel: Int,
    val minItemLevel: Int,
    val startTime: String,
    val endTime: String,
    val image: String
) {
    fun toEntity(): GuardianRaid {
        return GuardianRaid(
            name,
            description,
            minCharacterLevel,
            minItemLevel,
            startTime,
            endTime,
            image
        )
    }
}