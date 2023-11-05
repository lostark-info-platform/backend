package org.info.lostark.schedule.command.domain

import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.collections.shouldHaveSize
import org.info.lostark.fixture.createSchedule
import org.info.lostark.fixture.createUser
import org.info.lostark.support.RepositoryTest
import org.info.lostark.user.command.domain.UserRepository

@RepositoryTest
class ScheduleRepositoryTest(
    private val scheduleRepository: ScheduleRepository,
    private val userRepository: UserRepository
) : ExpectSpec({
    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    context("스케줄 조회") {
        val user = userRepository.save(createUser())
        val schedule = createSchedule(user = user)
        scheduleRepository.save(schedule)

        expect("유저의 모든 스케줄을 조회한다") {
            val actual = scheduleRepository.findAllByUser(user)
            actual shouldHaveSize 1
        }

        expect("유저는 스케줄을 삭제할 수 있다") {
            scheduleRepository.deleteById(schedule.id)
            val actual = scheduleRepository.findAll()
            actual shouldHaveSize 0
        }
    }
})

