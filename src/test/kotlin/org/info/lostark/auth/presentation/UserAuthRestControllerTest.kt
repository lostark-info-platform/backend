package org.info.lostark.auth.presentation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.info.lostark.auth.command.application.RefreshTokenService
import org.info.lostark.auth.command.application.ResignService
import org.info.lostark.auth.command.application.UserAuthenticationService
import org.info.lostark.auth.presentation.dto.AuthenticateUserRequest
import org.info.lostark.auth.presentation.dto.LogoutRequest
import org.info.lostark.auth.presentation.dto.RegisterUserRequest
import org.info.lostark.auth.presentation.dto.ResignRequest
import org.info.lostark.auth.presentation.dto.TokenRefreshRequest
import org.info.lostark.fixture.createJwtResponse
import org.info.lostark.support.RestControllerTest
import org.info.lostark.support.bearer
import org.info.lostark.user.command.domain.Password
import org.info.lostark.user.command.domain.UnidentifiedUserException
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.post

@WebMvcTest(UserAuthRestController::class)
class UserAuthRestControllerTest : RestControllerTest() {
    @MockkBean
    private lateinit var userAuthenticationService: UserAuthenticationService

    @MockkBean
    private lateinit var refreshTokenService: RefreshTokenService

    @MockkBean
    private lateinit var resignService: ResignService

    @Test
    fun `유효한 회원가입 요청시 토큰을 응답한다`() {
        val response = createJwtResponse()
        every { userAuthenticationService.generateTokenByRegister(any()) } returns response

        mockMvc.post("/api/users/register") {
            jsonContent(RegisterUserRequest("user", "user@email.com", Password("password"), Password("password")))
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("user-register-post"))
        }
    }

    @Test
    fun `회원가입 요청시 이미 존재하는 이메일일 시 400 BadRequest 응답한다`() {
        every { userAuthenticationService.generateTokenByRegister(any()) } throws IllegalArgumentException("이미 존재하는 이메일입니다.")

        mockMvc.post("/api/users/register") {
            jsonContent(RegisterUserRequest("user", "exist@email.com", Password("password"), Password("password")))
        }.andExpect {
            status { isBadRequest() }
        }.andDo {
            handle(document("user-register-post-already-exist-email"))
        }
    }

    @Test
    fun `유효한 로그인 요청시 토큰을 응답한다`() {
        val response = createJwtResponse()
        every { userAuthenticationService.generateTokenByLogin(any(), any()) } returns response

        mockMvc.post("/api/users/login") {
            jsonContent(AuthenticateUserRequest("user@email.com", Password("password")))
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("user-login-post"))
        }
    }

    @Test
    fun `로그인 시 이메일형식이 올바르지 않은 경우 에러 발생한다`() {
        val response = createJwtResponse()
        val invalidEmail = "invalid_email"
        every { userAuthenticationService.generateTokenByLogin(any(), any()) } returns response

        mockMvc.post("/api/users/login") {
            jsonContent(AuthenticateUserRequest(invalidEmail, Password("password")))
        }.andExpect {
            status { isBadRequest() }
        }.andDo {
            handle(document("user-login-post-invalid-email-form"))
        }
    }

    @Test
    fun `로그인 요청한 정보가 올바르지 않은 경우 403 Forbidden 반환한다`() {
        every {
            userAuthenticationService.generateTokenByLogin(
                any(),
                any()
            )
        } throws UnidentifiedUserException("사용자 정보가 일치하지 않습니다.")

        mockMvc.post("/api/users/login") {
            jsonContent(AuthenticateUserRequest("unmatched@email.com", Password("password")))
        }.andExpect {
            status { isForbidden() }
        }.andDo {
            handle(document("user-login-post-forbidden"))
        }
    }

    @Test
    fun `유효한 리프레시 토큰으로 로그인 요청시 토큰을 응답한다`() {
        val response = createJwtResponse()
        val request = TokenRefreshRequest("valid_refreshToken")

        every { userAuthenticationService.generateTokenByRefreshToken(request.refreshToken) } returns response
        mockMvc.post("/api/users/refresh") {
            jsonContent(request)
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("user-refresh-post"))
        }
    }

    @Test
    fun `로그아웃 요청시 NoContent를 반환한다`() {
        val request = LogoutRequest("refresh_token")
        every { refreshTokenService.revoke(request.refreshToken) } just Runs

        mockMvc.post("/api/users/logout") {
            jsonContent(request)
            bearer("valid_token")
        }.andExpect {
            status { isNoContent() }
        }.andDo {
            handle(document("user-logout-post"))
        }
    }

    @Test
    fun `회원탈퇴 요청시 NoContent를 반환한다`() {
        val request = ResignRequest(Password("password"))
        every { resignService.resign(any(), any()) } just Runs

        mockMvc.post("/api/users/resign") {
            jsonContent(request)
            bearer("valid_token")
        }.andExpect {
            status { isNoContent() }
        }.andDo {
            handle(document("user-resign-post"))
        }
    }
}
