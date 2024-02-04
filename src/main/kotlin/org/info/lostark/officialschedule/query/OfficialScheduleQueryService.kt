package org.info.lostark.officialschedule.query

import org.info.lostark.officialschedule.command.dommain.ContentsCalendarPrimaryCategory
import org.info.lostark.officialschedule.command.dommain.OtherContentsType
import org.info.lostark.officialschedule.query.dao.OfficialScheduleQueryDao
import org.info.lostark.officialschedule.query.dto.ChallengeAbyssDungeonResponse
import org.info.lostark.officialschedule.query.dto.ContentsCalendarResponse
import org.info.lostark.officialschedule.query.dto.RootRaidResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
@Transactional(readOnly = true)
class OfficialScheduleQueryService(
    private val officialScheduleQueryDao: OfficialScheduleQueryDao
) {
    fun getSummary(): List<OfficialContent> {
        val result = mutableListOf<OfficialContent>()

        val nearestContentsCalendars = getNearestContentsCalendars()
        result.addAll(nearestContentsCalendars)

        val nearestAbyssDungeon = getNearestAbyssDungeon()
        result.add(nearestAbyssDungeon)

        val nearestGuardianRaid = getNearestGuardianRaid()
        result.add(nearestGuardianRaid)
        return result
    }

    private fun getNearestContentsCalendars(): List<OfficialContent> {
        val contentsCalendars = officialScheduleQueryDao.findSummaryContentsCalendar()
        val officialContents = ContentsCalendarPrimaryCategory.values().map {
            createOfficialContentFromContentsCalendar(it, contentsCalendars)
        }
        return officialContents
    }

    private fun getNearestAbyssDungeon(): OfficialContent {
        val abyssDungeons = officialScheduleQueryDao.findSummaryAbyssDungeon()
        return createOfficialContentFromAbyssDungeon(abyssDungeons)
    }

    private fun getNearestGuardianRaid(): OfficialContent {
        val guardians = officialScheduleQueryDao.findSummaryGuardian()
        return createOfficialContentFromGuardian(guardians)
    }

    private fun createOfficialContentFromContentsCalendar(
        category: ContentsCalendarPrimaryCategory,
        contentsCalendars: List<ContentsCalendarResponse>
    ): OfficialContent {
        val filteredByCategory = contentsCalendars
            .filter { it.categoryName == category.value }
        val nearestStartTime = filteredByCategory
            .flatMap { it.startTimes }
            .findNearestFuture()
        return OfficialContent(category.iconUrl, category.value, nearestStartTime)
    }

    private fun createOfficialContentFromAbyssDungeon(abysses: List<ChallengeAbyssDungeonResponse>): OfficialContent {
        val nearestStartTime = abysses
            .map { it.startTime }
            .findNearestFuture()
        return OfficialContent(
            OtherContentsType.ABYSS_DUNGEON.iconUrl,
            OtherContentsType.ABYSS_DUNGEON.value,
            nearestStartTime
        )
    }

    private fun createOfficialContentFromGuardian(rootRaidResponse: RootRaidResponse): OfficialContent {
        val nearestStartTime = rootRaidResponse
            .raids
            .map { it.startTime }
            .findNearestFuture()
        return OfficialContent(
            OtherContentsType.GUARDIAN_RAID.iconUrl,
            OtherContentsType.GUARDIAN_RAID.value,
            nearestStartTime
        )
    }
}

fun List<LocalDateTime>.findNearestFuture() =
    this.filter { startTime -> startTime.isAfter(LocalDateTime.now()) }.minOrNull()

data class OfficialContent(
    val iconUrl: String,
    val name: String,
    val startedAt: LocalDateTime?,
)
