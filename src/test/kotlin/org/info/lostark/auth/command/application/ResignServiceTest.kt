package org.info.lostark.auth.command.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.*
import org.info.lostark.fixture.createUser
import org.info.lostark.support.afterRootTest
import org.info.lostark.user.command.domain.Password
import org.info.lostark.user.command.domain.UnidentifiedUserException
import org.info.lostark.user.command.domain.UserRepository
import org.info.lostark.user.command.domain.getOrThrow

class ResignServiceTest : BehaviorSpec({
    val refreshTokenService = mockk<RefreshTokenService>()
    val userRepository = mockk<UserRepository>()

    val resignService = ResignService(refreshTokenService, userRepository)

    Given("유저정보가 존재하는 경우") {
        every { userRepository.getOrThrow(any()) } returns createUser()
        every { refreshTokenService.revokeAll(any()) } just Runs

        When("올바른 비밀번호로 회원탈퇴 하면") {
            resignService.resign(1L, Password("password"))

            Then("리프레쉬토큰을 삭제한다") {
                verify(exactly = 1) { refreshTokenService.revokeAll(1L) }
            }
        }

        When("올바르지 않은 비밀번호로 회원탈퇴 하면") {
            Then("에러 발생한다") {
                shouldThrow<UnidentifiedUserException> {
                    resignService.resign(
                        1L,
                        Password("anotherPassword")
                    )
                }
            }
        }
    }

    afterRootTest {
        clearAllMocks()
    }
})
