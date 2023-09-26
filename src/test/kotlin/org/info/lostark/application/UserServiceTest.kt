package org.info.lostark.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.info.lostark.domain.user.UserRepository
import org.info.lostark.domain.user.findByEmail
import org.info.lostark.fixture.createUser
import org.info.lostark.support.afterRootTest

class UserServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()

    val userService = UserService(userRepository)

    Given("유저 이메일이 존재하는 경우") {
        val user = createUser()
        every { userRepository.findByEmail(any()) } returns user

        When("이메일을 통해 유저를 찾으면") {
            val actual = userService.getByEmail(user.email)

            Then("유저 정보를 응답한다") {
                actual.information.name shouldBe user.information.name
            }
        }
    }

    Given("유저 이메일이 존재하지 않는 경우") {
        every { userRepository.findByEmail(any()) } returns null

        When("이메일을 통해 유저를 찾으면") {
            Then("에러 발생한다") {
                shouldThrow<IllegalArgumentException> { userService.getByEmail("user@email.com") }
            }
        }
    }

    afterRootTest {
        clearAllMocks()
    }
})
