package org.info.lostark.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.info.lostark.domain.user.Password
import org.info.lostark.domain.user.UnidentifiedUserException
import org.info.lostark.domain.user.UserRepository
import org.info.lostark.domain.user.getOrThrow
import org.info.lostark.fixture.createUser
import org.info.lostark.support.afterRootTest

class ResignServiceTest : BehaviorSpec({
    val refreshTokenService = mockk<RefreshTokenService>()
    val userRepository = mockk<UserRepository>()

    val resignService = ResignService(refreshTokenService, userRepository)

    Given("유저정보가 존재하는 경우") {
        every { userRepository.getOrThrow(any()) } returns createUser()
        every { refreshTokenService.revokeAll(any()) } just Runs

        When("올바른 비밀번호로 회원탈퇴 하면") {
            resignService.resign(1L, ResignRequest(Password("password")))

            Then("리프레쉬토큰을 삭제한다") {
                verify(exactly = 1) { refreshTokenService.revokeAll(1L) }
            }
        }

        When("올바르지 않은 비밀번호로 회원탈퇴 하면") {
            Then("에러 발생한다") {
                shouldThrow<UnidentifiedUserException> {
                    resignService.resign(
                        1L,
                        ResignRequest(Password("anotherPassword"))
                    )
                }
            }
        }
    }

    afterRootTest {
        clearAllMocks()
    }
})
