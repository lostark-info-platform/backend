package org.info.lostark.common.config

import java.time.LocalDateTime
import org.info.lostark.schedule.command.domain.Schedule
import org.info.lostark.schedule.command.domain.ScheduleRepository
import org.info.lostark.user.command.domain.Password
import org.info.lostark.user.command.domain.User
import org.info.lostark.user.command.domain.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

//@Profile("local") 실제 운영해야할 때 주석 해제
@Transactional
@Component
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val scheduleRepository: ScheduleRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        populate()
    }

    private fun populate() {
        populateUser()
        populateSchedule()
    }

    private fun populateUser() {
        val users = listOf(
            User("홍길동", "gildong@email.com", Password("password")),
            User("이순신", "sunsin@email.com", Password("password")),
            User("김세종", "sejong@email.com", Password("password"))
        )
        userRepository.saveAll(users)
    }

    private fun populateSchedule() {
        val schedules = userRepository.findAll().flatMap { user ->
            (1..3).map {
                val startDate = getRandomStartDate()
                Schedule.of(
                    user,
                    "${user.name}의 스케줄 제목 $it",
                    startDate,
                    getRandomEndDateAfterStart(startDate),
                    randomCategory(),
                    listOf(5, 10, 15, 20, 25, 30).random(),
                    "${user.name}의 메모${it}"
                )
            }
        }
        scheduleRepository.saveAll(schedules)
    }

    private fun randomCategory(): String {
        return listOf("레이드", "일일숙제", "장비강화", "PVP").random()
    }

    private fun getRandomStartDate(): LocalDateTime {
        return LocalDateTime.now().plusRandomDateTime()
    }

    private fun getRandomEndDateAfterStart(startDateTime: LocalDateTime): LocalDateTime {
        return startDateTime.plusRandomDateTime()
    }

    fun LocalDateTime.plusRandomDateTime(): LocalDateTime {
        return this.plusDays((0L..6L).random())
            .plusHours((1L..23L).random())
            .withMinute(0)
            .withSecond(0)
    }
}