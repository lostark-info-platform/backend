package org.info.lostark.schedule.command.application

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.info.lostark.fixture.createSchedule
import org.info.lostark.fixture.createUser
import org.info.lostark.schedule.command.domain.ScheduleRepository
import org.info.lostark.schedule.command.domain.ScheduleState.DONE
import org.info.lostark.schedule.command.domain.ScheduleState.TODO
import org.info.lostark.schedule.command.domain.getOrThrow
import org.info.lostark.support.afterRootTest

class ScheduleCheckServiceTest : BehaviorSpec({
    val scheduleRepository = mockk<ScheduleRepository>()
    val scheduleCheckService = ScheduleCheckService(scheduleRepository)
    Given("Schedule이 존재하는경우") {
        val user = createUser()
        val schedule = createSchedule(user = user)
        every { scheduleRepository.getOrThrow(any()) } returns schedule

        When("체크를 호출 하면") {
            scheduleCheckService.check(user.id, schedule.id)

            Then("스케쥴이 DONE 상태로 변한다") {
                schedule.state shouldBe DONE
            }
        }

        When("체크해제를 호출 하면") {
            scheduleCheckService.uncheck(user.id, schedule.id)

            Then("스케쥴이 TODO 상태로 변한다") {
                schedule.state shouldBe TODO
            }
        }
    }

    afterRootTest {
        clearAllMocks()
    }
})
