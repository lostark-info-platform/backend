package org.info.lostark.officialschedule.query.dto

import java.time.LocalDateTime

data class OfficialContentsDetailsQueryResponse(
    val startedAt: LocalDateTime?,
    val contents: List<OfficialContentResponse>
)

data class OfficialContentResponse(
    val contentsName: String, // 컨텐츠캘린더, 어비스
    val contentsIcon: String?, // 컨텐츠캘린더
    val minItemLevel: Int, // 컨텐츠캘린더, 어비스
    val location: String?, // 컨텐츠캘린더
    val minCharacterLevel: Int?, // 어비스
    val image: String?, // 어비스
    val areaName: String?, // 어비스
    val description: String?, // 어비스
    val rewardItems: List<RewardItemResponse> // 컨텐츠캘린더, 어비스

) {
    constructor(contentsCalendar: ContentsCalendarResponse) : this(
        contentsName = contentsCalendar.contentsName,
        contentsIcon = contentsCalendar.contentsIcon,
        minItemLevel = contentsCalendar.minItemLevel,
        location = contentsCalendar.location,
        minCharacterLevel = null,
        image = null,
        areaName = null,
        description = null,
        rewardItems = contentsCalendar.rewardItems
    )

    constructor(abyssDungeon: ChallengeAbyssDungeonResponse) : this(
        contentsName = abyssDungeon.name,
        contentsIcon = null,
        minItemLevel = abyssDungeon.minItemLevel,
        location = null,
        minCharacterLevel = abyssDungeon.minCharacterLevel,
        image = abyssDungeon.image,
        areaName = abyssDungeon.areaName,
        description = abyssDungeon.description,
        rewardItems = abyssDungeon.rewardItems
    )

}
