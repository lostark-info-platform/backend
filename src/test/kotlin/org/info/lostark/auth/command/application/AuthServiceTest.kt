package org.info.lostark.auth.command.application

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.info.lostark.fixture.createRefreshToken
import org.info.lostark.fixture.createUser
import org.info.lostark.support.afterRootTest
import org.junit.jupiter.api.assertThrows

class AuthServiceTest : BehaviorSpec({
    val refreshTokenService = mockk<RefreshTokenService>()
    val jwtProvider = mockk<JwtProvider>()
    val authService = AuthService(refreshTokenService, jwtProvider)

    Given("리프레시 토큰이 유효한 경우") {
        val refreshToken = "valid_token"
        val user = createUser()
        every { jwtProvider.generateAccessToken(any()) } returns "access_token"
        every { jwtProvider.generateRefreshToken(any()) } returns "refresh_token"
        every { jwtProvider.isValidToken("valid_token") } returns true
        every { refreshTokenService.rotate(any(), any()) } just Runs
        every { refreshTokenService.findByJwt(refreshToken) } returns createRefreshToken(user)

        When("엑세스토큰을 요청하면") {
            val actual = authService.generateTokenByRefreshToken(refreshToken)

            Then("리프레시토큰을 로테이션한다") {
                verify(exactly = 1) { refreshTokenService.rotate(any(), any()) }
                actual.accessToken shouldBe "access_token"
            }
        }
    }

    Given("리프레시 토큰이 유효하지 않은 경우") {
        val refreshToken = "invalid_token"
        every { jwtProvider.isValidToken("invalid_token") } returns false

        When("엑세스토큰을 요청하면") {
            Then("에러 발생한다") {
                assertThrows<IllegalArgumentException> { authService.generateTokenByRefreshToken(refreshToken) }
            }
        }
    }

    afterRootTest {
        clearAllMocks()
    }
})
