package org.info.lostark.officialschedule.query.dto

import org.info.lostark.officialschedule.command.dommain.GuardianRaid
import org.info.lostark.officialschedule.command.dommain.GuardianRaidRewardItem
import org.info.lostark.officialschedule.command.dommain.RootRaid

data class RootRaidResponse(
    val raids: List<GuardianRaidResponse>,
    val items: List<GuardianRaidRewardItemResponse>
) {
    constructor(rootRaid: RootRaid) : this(
        rootRaid.raids.map { GuardianRaidResponse(it) },
        rootRaid.items.map { GuardianRaidRewardItemResponse(it) }
    )
}

data class GuardianRaidResponse(
    val name: String,
    val description: String,
    val minCharacterLevel: Int,
    val minItemLevel: Int,
    val startTime: String,
    val endTime: String,
    val image: String,
) {
    constructor(raid: GuardianRaid) : this(
        raid.name,
        raid.description,
        raid.minCharacterLevel,
        raid.minItemLevel,
        raid.startTime,
        raid.endTime,
        raid.image
    )
}

data class GuardianRaidRewardItemResponse(
    val expeditionItemLevel: Int,
    val rewardItems: List<RewardItemResponse>
) {
    constructor(guardianRaidRewardItem: GuardianRaidRewardItem) : this(
        guardianRaidRewardItem.expeditionItemLevel,
        guardianRaidRewardItem.rewardItems.map { RewardItemResponse(it) }
    )
}