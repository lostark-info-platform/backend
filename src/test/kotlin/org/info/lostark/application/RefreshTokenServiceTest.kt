package org.info.lostark.application

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.info.lostark.application.auth.RefreshTokenService
import org.info.lostark.application.user.LogoutRequest
import org.info.lostark.domain.user.RefreshTokenByUserRepository
import org.info.lostark.domain.user.RefreshTokenRepository
import org.info.lostark.domain.user.UserRepository
import org.info.lostark.domain.user.getOrThrow
import org.info.lostark.fixture.createRefreshToken
import org.info.lostark.fixture.createRefreshTokenByUser
import org.info.lostark.support.afterRootTest
import org.springframework.data.repository.findByIdOrNull

class RefreshTokenServiceTest : BehaviorSpec({
    val refreshTokenRepository = mockk<RefreshTokenRepository>()
    val refreshTokenByUserRepository = mockk<RefreshTokenByUserRepository>()
    val userRepository = mockk<UserRepository>()

    val refreshTokenService = RefreshTokenService(refreshTokenRepository, refreshTokenByUserRepository, userRepository)

    Given("리프레쉬토큰이 존재하는 경우") {
        val refreshToken = createRefreshToken()
        every { refreshTokenRepository.getOrThrow(any()) } returns refreshToken
        every { refreshTokenRepository.delete(any()) } just Runs

        When("단건 삭제를 요청하면") {
            refreshTokenService.revoke(LogoutRequest(refreshToken.id))

            Then("리프레쉬토큰을 삭제한다") {
                verify { refreshTokenRepository.delete(any()) }
            }
        }
    }

    Given("리프레쉬토큰이 다중건 존재하는 경우") {
        val refreshTokenByUser = createRefreshTokenByUser()
        every { refreshTokenByUserRepository.findByIdOrNull(any()) } returns refreshTokenByUser
        every { refreshTokenRepository.deleteAllById(refreshTokenByUser.tokens) } just Runs

        When("다중건 삭제를 요청하면") {
            refreshTokenService.revokeAll(refreshTokenByUser.id)

            Then("리프레쉬토큰을 다중건 삭제한다") {
                verify(exactly = 1) { refreshTokenRepository.deleteAllById(any()) }
            }
        }
    }

    afterRootTest {
        clearAllMocks()
    }
})
