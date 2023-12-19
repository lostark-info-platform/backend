package org.info.lostark.common.domain

import org.info.lostark.common.config.SmileGateProperties
import org.info.lostark.officialschedule.command.dommain.ChallengeAbyssDungeonRepository
import org.info.lostark.officialschedule.command.dommain.ContentsCalendarRepository
import org.info.lostark.officialschedule.command.dommain.OfficialScheduleGetter
import org.info.lostark.officialschedule.command.dommain.RootRaidRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val officialScheduleGetter: OfficialScheduleGetter,
    private val smileGateProperties: SmileGateProperties,
    private val challengeAbyssDungeonRepository: ChallengeAbyssDungeonRepository,
    private val contentsCalendarRepository: ContentsCalendarRepository,
    private val rootRaidRepository: RootRaidRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        challengeAbyssDungeonRepository.deleteAllInBatch()
        contentsCalendarRepository.deleteAllInBatch()
        rootRaidRepository.deleteAllInBatch()

        challengeAbyssDungeonRepository.saveAll(
            officialScheduleGetter.fetchAllChallengeAbyssDungeon(smileGateProperties.accessToken)
        )
        contentsCalendarRepository.saveAll(
            officialScheduleGetter.fetchAllContentsCalendar(smileGateProperties.accessToken)
        )
        rootRaidRepository.save(
            officialScheduleGetter.fetchRootGuardianRaids(smileGateProperties.accessToken)
        )
    }
}