package org.info.lostark.officialschedule.query

import org.info.lostark.officialschedule.command.dommain.ContentsCalendarPrimaryCategory
import org.info.lostark.officialschedule.command.dommain.ContentsCategory
import org.info.lostark.officialschedule.command.dommain.OtherContentsType
import org.info.lostark.officialschedule.presentation.Contents
import org.info.lostark.officialschedule.query.dao.OfficialScheduleQueryDao
import org.info.lostark.officialschedule.query.dto.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
@Transactional(readOnly = true)
class OfficialScheduleQueryService(
    private val officialScheduleQueryDao: OfficialScheduleQueryDao
) {
    fun getSummary(): List<OfficialContentSummaryResponse> {
        val result = mutableListOf<OfficialContentSummaryResponse>()

        val nearestContentsCalendars = getNearestContentsCalendars()
        result.addAll(nearestContentsCalendars)

        val nearestAbyssDungeon = getNearestAbyssDungeon()
        result.add(nearestAbyssDungeon)

        val nearestGuardianRaid = getNearestGuardianRaid()
        result.add(nearestGuardianRaid)
        return result
    }

    fun getContents(category: Contents, location: String?): OfficialContentsDetailsQueryResponse {
        val contentsCategory = category.toDomain()
        if (contentsCategory is ContentsCalendarPrimaryCategory) {
            val contentsCalendars = location?.let {
                findAllContentsCalendars(contentsCategory)
                    .filter { it.location == location }
            } ?: findAllContentsCalendars(contentsCategory)
            val startedAt = contentsCalendars.flatMap { it.startTimes }.findNearestFuture()
            val contents = contentsCalendars
                .filter { it.startTimes.contains(startedAt) }
                .map { OfficialContentResponse(it) }
            return OfficialContentsDetailsQueryResponse(startedAt, contents)
        }

        if (contentsCategory == OtherContentsType.ABYSS_DUNGEON) {
            val abyssDungeon = officialScheduleQueryDao.findAllAbyssDungeon()
            val startedAt = abyssDungeon.map { it.startTime }.findNearestFuture()
            val contents = abyssDungeon.filter { it.startTime == startedAt }.map { OfficialContentResponse(it) }
            return OfficialContentsDetailsQueryResponse(startedAt, contents)
        }

        if (contentsCategory == OtherContentsType.GUARDIAN_RAID) {
            return OfficialContentsDetailsQueryResponse(null, emptyList())
        }

        throw IllegalArgumentException()
    }

    private fun findAllContentsCalendars(category: ContentsCategory): List<ContentsCalendarResponse> {
        val findAllContentsCalendars = officialScheduleQueryDao.findAllContentsCalendar()
        return findAllContentsCalendars
            .filter { it.categoryName == category.value }
    }

    fun getFieldBossLocation(): List<String> {
        return officialScheduleQueryDao.findAllContentsCalendar()
            .filter { it.categoryName == ContentsCalendarPrimaryCategory.FIELD_BOSS.value }
            .map { it.location }
            .sorted()
    }

    private fun getNearestContentsCalendars(): List<OfficialContentSummaryResponse> {
        val contentsCalendars = officialScheduleQueryDao.findAllContentsCalendar()
        val officialContents = ContentsCalendarPrimaryCategory.values().map {
            createOfficialContentFromContentsCalendar(it, contentsCalendars)
        }
        return officialContents
    }

    private fun getNearestAbyssDungeon(): OfficialContentSummaryResponse {
        val abyssDungeons = officialScheduleQueryDao.findAllAbyssDungeon()
        return createOfficialContentFromAbyssDungeon(abyssDungeons)
    }

    private fun getNearestGuardianRaid(): OfficialContentSummaryResponse {
        val guardians = officialScheduleQueryDao.findSummaryGuardian()
        return createOfficialContentFromGuardian(guardians)
    }

    private fun createOfficialContentFromContentsCalendar(
        category: ContentsCalendarPrimaryCategory,
        contentsCalendars: List<ContentsCalendarResponse>
    ): OfficialContentSummaryResponse {
        val filteredByCategory = contentsCalendars
            .filter { it.categoryName == category.value }
        val nearestStartTime = filteredByCategory
            .flatMap { it.startTimes }
            .findNearestFuture()
        return OfficialContentSummaryResponse(category.iconUrl, category.value, nearestStartTime)
    }

    private fun createOfficialContentFromAbyssDungeon(abysses: List<ChallengeAbyssDungeonResponse>): OfficialContentSummaryResponse {
        val nearestStartTime = abysses
            .map { it.startTime }
            .findNearestFuture()
        return OfficialContentSummaryResponse(
            OtherContentsType.ABYSS_DUNGEON.iconUrl,
            OtherContentsType.ABYSS_DUNGEON.value,
            nearestStartTime
        )
    }

    private fun createOfficialContentFromGuardian(rootRaidResponse: RootRaidResponse): OfficialContentSummaryResponse {
        val nearestStartTime = rootRaidResponse
            .raids
            .map { it.startTime }
            .findNearestFuture()
        return OfficialContentSummaryResponse(
            OtherContentsType.GUARDIAN_RAID.iconUrl,
            OtherContentsType.GUARDIAN_RAID.value,
            nearestStartTime
        )
    }
}

fun List<LocalDateTime>.findNearestFuture() =
    this.filter { startTime -> startTime.isAfter(LocalDateTime.now()) }.minOrNull()
