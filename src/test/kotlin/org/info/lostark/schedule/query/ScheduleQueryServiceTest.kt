package org.info.lostark.schedule.query

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.mockk.every
import io.mockk.mockk
import org.info.lostark.fixture.createScheduleQueryResponse
import org.info.lostark.schedule.query.dao.ScheduleQueryDao

class ScheduleQueryServiceTest : BehaviorSpec({
    val scheduleQueryDao = mockk<ScheduleQueryDao>()
    val scheduleQueryService = ScheduleQueryService(scheduleQueryDao)

    Given("유저의 스케줄이 존재하는 경우") {
        val response = listOf(
            createScheduleQueryResponse(scheduleId = 1L),
            createScheduleQueryResponse(scheduleId = 2L),
            createScheduleQueryResponse(scheduleId = 3L)
        )
        every { scheduleQueryDao.findAllScheduleByUserId(any()) } returns response

        When("유저의 스케줄을 요청하면") {
            val actual = scheduleQueryService.findAllScheduleByUserId(1L)

            Then("유저의 스케줄을 응답한다") {
                actual.size shouldBeGreaterThan 0
            }
        }
    }
})
