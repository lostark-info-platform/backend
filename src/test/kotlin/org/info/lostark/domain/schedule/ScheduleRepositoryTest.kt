package org.info.lostark.domain.schedule

import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.collections.shouldHaveSize
import org.info.lostark.domain.user.UserRepository
import org.info.lostark.fixture.createSchedule
import org.info.lostark.fixture.createUser
import org.info.lostark.support.RepositoryTest

@RepositoryTest
class ScheduleRepositoryTest(
    private val scheduleRepository: ScheduleRepository,
    private val userRepository: UserRepository
) : ExpectSpec({
    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    context("스케줄 조회") {
        val user = userRepository.save(createUser())
        scheduleRepository.save(createSchedule(user = user))

        expect("유저의 모든 스케줄을 조회한다") {
            val actual = scheduleRepository.findAllByUser(user)
            actual shouldHaveSize 1
        }
    }
})

