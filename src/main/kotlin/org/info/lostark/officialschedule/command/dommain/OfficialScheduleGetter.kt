package org.info.lostark.officialschedule.command.dommain

interface OfficialScheduleGetter {
    fun fetchAllChallengeAbyssDungeon(accessToken: String): List<ChallengeAbyssDungeon>
    fun fetchAllContentsCalendar(accessToken: String): List<ContentsCalendar>
    fun fetchRootGuardianRaids(accessToken: String): RootRaid
}