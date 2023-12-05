package org.info.lostark.officialschedule.query.dto

import org.info.lostark.officialschedule.command.dommain.RewardItem
import java.time.LocalDateTime

data class RewardItemResponse(
    val name: String,
    val icon: String,
    val grade: String,
    val startTimes: List<LocalDateTime>?
) {
    constructor(rewardItem: RewardItem) : this(
        rewardItem.name,
        rewardItem.icon,
        rewardItem.grade,
        rewardItem.startTimes?.split(",")?.map { LocalDateTime.parse(it) }
    )
}

