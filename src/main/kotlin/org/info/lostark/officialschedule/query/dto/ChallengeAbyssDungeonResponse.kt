package org.info.lostark.officialschedule.query.dto

import org.info.lostark.officialschedule.command.dommain.ChallengeAbyssDungeon

data class ChallengeAbyssDungeonResponse(
    val name: String,
    val description: String,
    val minCharacterLevel: Int,
    val minItemLevel: Int,
    val areaName: String,
    val startTime: String,
    val endTime: String,
    val image: String,
    val rewardItems: List<RewardItemResponse>,
) {
    constructor(challengeAbyssDungeon: ChallengeAbyssDungeon) : this(
        challengeAbyssDungeon.name,
        challengeAbyssDungeon.description,
        challengeAbyssDungeon.minCharacterLevel,
        challengeAbyssDungeon.minItemLevel,
        challengeAbyssDungeon.areaName,
        challengeAbyssDungeon.startTime,
        challengeAbyssDungeon.endTime,
        challengeAbyssDungeon.image,
        challengeAbyssDungeon.rewardItems.map { RewardItemResponse(it) }
    )
}

