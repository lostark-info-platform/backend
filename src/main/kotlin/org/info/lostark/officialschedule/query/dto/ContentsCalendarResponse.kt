package org.info.lostark.officialschedule.query.dto

import org.info.lostark.officialschedule.command.dommain.ContentsCalendar
import java.time.LocalDateTime

data class ContentsCalendarResponse(
    val categoryName: String,
    val contentsName: String,
    val contentsIcon: String,
    val minItemLevel: Int,
    val startTimes: List<LocalDateTime>,
    val location: String,
    val rewardItems: List<RewardItemResponse>
) {
    constructor(contentsCalendar: ContentsCalendar) : this(
        contentsCalendar.categoryName,
        contentsCalendar.contentsName,
        contentsCalendar.contentsIcon,
        contentsCalendar.minItemLevel,
        contentsCalendar.startTimes.split(",").map { LocalDateTime.parse(it) },
        contentsCalendar.location,
        contentsCalendar.rewardItems.map { RewardItemResponse(it) }
    )

}
