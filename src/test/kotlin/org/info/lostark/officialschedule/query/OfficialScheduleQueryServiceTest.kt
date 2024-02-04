package org.info.lostark.officialschedule.query

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
class OfficialScheduleQueryServiceTest(
    @Autowired val officialScheduleQueryService: OfficialScheduleQueryService
) {
    @Test
    fun `서머리를 조회한다`() {
        officialScheduleQueryService.getSummary()
    }
}