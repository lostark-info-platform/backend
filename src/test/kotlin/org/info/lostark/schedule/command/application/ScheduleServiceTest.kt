package org.info.lostark.schedule.command.application

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.info.lostark.fixture.createSchedule
import org.info.lostark.fixture.createScheduleRegisterRequest
import org.info.lostark.fixture.createScheduleUpdateRequest
import org.info.lostark.fixture.createUser
import org.info.lostark.schedule.command.domain.ScheduleRepository
import org.info.lostark.schedule.command.domain.getOrThrow
import org.info.lostark.support.afterRootTest
import org.info.lostark.user.command.domain.UserRepository
import org.info.lostark.user.command.domain.getOrThrow

class ScheduleServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val scheduleRepository = mockk<ScheduleRepository>()
    val scheduleService = ScheduleService(userRepository, scheduleRepository)

    Given("유저가 존재하는 경우") {
        val user = createUser()
        val command = createScheduleRegisterRequest().toCommand(user.id)
        every { userRepository.getOrThrow(any()) } returns user
        every { scheduleRepository.save(any()) } returns createSchedule()

        When("스케줄 등록을 요청하면") {
            scheduleService.register(command)

            Then("스케줄이 저장된다") {
                verify(exactly = 1) { scheduleRepository.save(any()) }
            }
        }
    }

    Given("스케줄이 존재하는 경우") {
        val user = createUser()
        val schedule = createSchedule(user = user)
        val command = createScheduleUpdateRequest(title = "업데이트타이틀").toCommand(user.id, schedule.id)
        every { scheduleRepository.getOrThrow(any()) } returns schedule
        every { scheduleRepository.delete(any()) } just Runs

        When("스케줄 업데이트 요청하면") {
            scheduleService.update(command)

            Then("스케줄이 업데이트 된다") {
                schedule.title shouldBe "업데이트타이틀"
            }
        }

        When("스케줄 삭제요청하면") {
            scheduleService.delete(user.id, schedule.id)

            Then("스케줄이 삭제된다") {
                verify(exactly = 1) { scheduleRepository.delete(any()) }
            }
        }
    }

    afterRootTest {
        clearAllMocks()
    }
})
